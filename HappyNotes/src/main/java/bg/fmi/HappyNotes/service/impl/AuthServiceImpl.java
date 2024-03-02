package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.configuration.JwtService;
import bg.fmi.HappyNotes.exceptions.UserAlreadyExistsException;
import bg.fmi.HappyNotes.exceptions.UserCredentialsMismatchException;
import bg.fmi.HappyNotes.exceptions.UserNotFoundException;
import bg.fmi.HappyNotes.model.AuthRequest;
import bg.fmi.HappyNotes.model.AuthResponse;
import bg.fmi.HappyNotes.model.Gender;
import bg.fmi.HappyNotes.model.Gratitude;
import bg.fmi.HappyNotes.model.Habit;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.RegisterRequest;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.Token;
import bg.fmi.HappyNotes.model.TokenType;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.GratitudeRepository;
import bg.fmi.HappyNotes.repository.HabitRepository;
import bg.fmi.HappyNotes.repository.NotificationRepository;
import bg.fmi.HappyNotes.repository.TokenRepository;
import bg.fmi.HappyNotes.repository.UserRepository;
import bg.fmi.HappyNotes.service.AuthService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final GratitudeRepository gratitudeRepository;
    private final NotificationRepository notificationRepository;
    private final HabitRepository habitRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        User user = verifyUser(authRequest);
        String token = getValidTokenOrCreateNew(user);
        return AuthResponse.builder().token(token).role(user.getRole()).build();
    }

    private User verifyUser(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UserCredentialsMismatchException("Invalid password");
        }
        ensureNotificationExists(user);
        return user;
    }

    private void ensureNotificationExists(User user) {
        if (user.getNotification() == null) {
            Notification notification = new Notification();
            notification.setUser(user); // Set the bi-directional relationship
            notificationRepository.save(notification); // Persist the notification
            user.setNotification(notification); // Ensure the user is aware of the new notification
            // No need to save the user here as the relationship is managed and will be cascaded upon transaction commit
        }
    }

    @Transactional
    private String getValidTokenOrCreateNew(User user) {
        this.filterTokens();

        Optional<Token> repositoryToken = tokenRepository.findByUserIdAndExpiredAtIsNull(user.getId())
                .stream()
                .sorted()
                .findFirst();
        if (repositoryToken.isPresent() && !jwtService.isTokenValid(repositoryToken.get().getToken(), user)) {
            tokenRepository.delete(repositoryToken.get());
            return repositoryToken.get().getToken();
        }

        return generateAndSaveToken(user);
    }

    private void filterTokens() {
        tokenRepository.findAll().stream()
                .filter(token -> jwtService.isTokenExpired(token.getToken()))
                .forEach(token -> {
                    token.setExpiredAt(LocalDateTime.ofInstant(jwtService.extractExpiration(token.getToken()).toInstant(),
                            ZoneId.systemDefault()));
                    tokenRepository.save(token);
                });
    }

    private String generateAndSaveToken(User user) {
        try {
            String jwt = jwtService.generateToken(user);
            Token token = Token.builder().user(user).token(jwt).tokenType(TokenType.BEARER).build();
            tokenRepository.save(token);
            return jwt;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        User user = createUser(registerRequest);
        String jwt = generateAndSaveToken(user);
        return AuthResponse.builder().token(jwt).role(user.getRole()).build();
    }

    @Override
    @Transactional
    public Boolean validateToken(String token) {
        try {
            this.filterTokens();
            String username = jwtService.extractUsername(token);
            return username != null && userRepository.findByUsername(username)
                    .filter(user -> jwtService.isTokenValid(token, user))
                    .flatMap(user -> tokenRepository.findByToken(token)
                            .stream()
                            .filter(token1 -> jwtService.isTokenExpired(token1.getToken()))
                            .findFirst()
                    )
                    .map(tokenEntity -> tokenEntity.getExpiredAt() == null)
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    private User createUser(RegisterRequest registerRequest) {
        User user = User.builder()
                .age(registerRequest.getAge())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.isPremium() ? Role.PREMIUM_USER : Role.USER) // Assuming a check for premium
                .pin(StringUtils.hasText(registerRequest.getPin()) ?
                        passwordEncoder.encode(registerRequest.getPin()) : null)
                .gender(registerRequest.getGender())
                .enabled(true)
                .build();
        user.setNotification(notificationRepository.save(Notification.builder().user(user).build()));
        return userRepository.save(user);
    }

    public AuthResponse registerAdminUser() {
        User admin = getOrCreateAdminUser();
        String tokenString = getOrCreateTokenForUser(admin);

        // Simplify the creation and handling of sample data
//        createSampleUsersAndData();

        return AuthResponse.builder().token(tokenString).build();
    }

    private void createSampleUsersAndData() {
        List<User> users = createSampleUsers();
        insertSampleData(); // Assuming this method properly handles the creation of sample habits and does not need refactoring
        userRepository.saveAll(users); // Save all users at once to optimize database interactions
    }

    @Transactional
    private List<User> createSampleUsers() {
        List<User> users = IntStream.rangeClosed(1, 5)
                .mapToObj(this::createUserWithDetails)
                .collect(Collectors.toList());

        // Save and return users with gratitudes and notifications properly set up
        return userRepository.saveAll(users);
    }


    private User createUserWithDetails(int i) {
        User user = User.builder()
                .username("user" + i)
                .age(20 + 5 * i)
                .password(passwordEncoder.encode("password" + i))
                .pin(passwordEncoder.encode("pin" + i))
                .enabled(true).jettons(100 * i)
                .role(i % 2 == 0 ? Role.USER : Role.PREMIUM_USER)
                .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                .quotes(new ArrayList<>())
                .build();

        Notification notification = Notification.builder().user(user).build();
        user.setNotification(notification);

        user.setNotification(notificationRepository.save(notification));
        return user;
    }

    private User getOrCreateAdminUser() {
        return userRepository.findByUsername("admin@mail")
                .orElseGet(this::createAdminUserWithGratitudes);
    }

    @Transactional
    public User createAdminUserWithGratitudes() {
        User admin = User.builder()
                .username("admin@mail")
                .enabled(true)
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();

        Notification notification = Notification.builder().user(admin).build();
        notificationRepository.save(notification);

        admin.setNotification(notification);
        admin = userRepository.save(admin);

        List<Gratitude> gratitudes = createSampleGratitudes(admin);
        admin.setGratitudes(gratitudes);
        gratitudeRepository.saveAll(gratitudes);

        return admin;
    }

    private List<Gratitude> createSampleGratitudes(User user) {
        List<Gratitude> gratitudes = Arrays.asList(
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(11))
                        .message("Thank you for your hard work")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(10))
                        .message("Great job on the project!")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(10))
                        .message("Great job on the project!")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(9))
                        .message("Great job on the project!")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(7))
                        .message("Great job on the project!")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(7))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(7))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(7))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(6))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(5))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(4))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(3))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(3))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(3))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(2))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build(),
                Gratitude.builder()
                        .createdDate(LocalDateTime.now().minusDays(2))
                        .message("Your contributions are highly valued")
                        .user(user)
                        .build()
        );
        return gratitudeRepository.saveAll(gratitudes); // Save all gratitudes at once
    }


    private String getOrCreateTokenForUser(User user) {
        return tokenRepository.findByUserId(user.getId())
                .stream()
                .map(Token::getToken)
                .findFirst()
                .orElseGet(() -> generateAndSaveToken(user));
    }

    public void insertSampleData() {
        List<Habit> habits = new ArrayList<>();
        habitRepository.saveAll(habits);
    }
}
