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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

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
    Optional<String> existingToken = getValidTokenIfExists(user);
    if (existingToken.isPresent()) {
      return AuthResponse.builder().token(existingToken.get()).role(user.getRole()).build();
    }
    String newToken = generateAndSaveToken(user);
    return AuthResponse.builder().token(newToken).role(user.getRole()).build();
  }

  private User verifyUser(AuthRequest authRequest) {
    User user = userRepository.findByUsername(authRequest.getUsername())
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
      throw new UserCredentialsMismatchException("Invalid password");
    }
    return user;
  }

  private Optional<String> getValidTokenIfExists(User user) {
    return tokenRepository.findByUserId(user.getId())
        .filter(token -> token.getExpiredAt() != null)
        .map(Token::getToken);
  }

  private String generateAndSaveToken(User user) {
    String jwt = jwtService.generateToken(user);
    Token token = Token.builder()
        .user(user)
        .token(jwt)
        .tokenType(TokenType.BEARER)
        .build();
    tokenRepository.save(token);
    return jwt;
  }

  @Override
  public AuthResponse register(RegisterRequest registerRequest) {
    if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("User with this email already exists");
    }

    User user = createUser(registerRequest);
    userRepository.save(user);
    String jwt = generateAndSaveToken(user);
    return AuthResponse.builder().token(jwt).role(user.getRole()).build();
  }

  @Override
  public Boolean validateToken(String token) {
    try {
      String username = jwtService.extractUsername(token);
      return userRepository.findByUsername(username)
          .filter(user -> jwtService.isTokenValid(token, user))
          .flatMap(user -> tokenRepository.findByToken(token))
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
        .role(Role.USER)
        .pin((registerRequest.getPin() == null || registerRequest.getPin().isBlank()) ? null
            : passwordEncoder.encode(registerRequest.getPin()))
        .gender(registerRequest.getGender())
        .build();
    userRepository.save(user);
    user.setNotification(Notification.builder().user(user).build());
    notificationRepository.save(user.getNotification());

    user.setGratitudes(Collections.emptyList());
    userRepository.save(user);
    return user;
  }

  public AuthResponse registerAdminUser() {
    User admin = getOrCreateAdminUser();
    String tokenString = getOrCreateTokenForUser(admin);

    List<User> users = createSampleUsers(); // Creating sample users with gratitudes
    insertSampleData(); // Creating sample habits and notifications

    userRepository.saveAll(users);
    return AuthResponse.builder().token(tokenString).build();
  }

  private List<User> createSampleUsers() {
    List<User> users = new ArrayList<>();
    // Create and add sample users
    for (int i = 1; i <= 5; i++) {
      User user = new User();
      user.setUsername("user" + i);
      user.setAge(20 + 5 * i);
      user.setPassword(passwordEncoder.encode("password" + i));
      user.setPin(passwordEncoder.encode("pin" + i));
      user.setEnabled(true);
      user.setJettons(100 * i);
      user.setRole(i % 2 == 0 ? Role.USER : Role.PREMIUM_USER);
      user.setGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
      user.setQuotes(new ArrayList<>());
      users.add(user);
    }
    userRepository.saveAll(users);

    users.forEach(user -> {
      List<Gratitude> gratitudes = createSampleGratitudes(user);
      user.setGratitudes(gratitudes);
      userRepository.save(user);
    });

    return users;
  }

  private User getOrCreateAdminUser() {
    return userRepository.findByUsername("admin@mail")
        .orElseGet(() -> {
          User admin = createAdminUserWithGratitudes();
          userRepository.save(admin);
          return admin;
        });
  }

  private User createAdminUserWithGratitudes() {
    User admin = User.builder()
        .username("admin@mail")
        .enabled(true)
        .password(passwordEncoder.encode("admin"))
        .role(Role.ADMIN)
        .build();
    userRepository.save(admin);
    List<Gratitude> gratitudes = createSampleGratitudes(admin);
    admin.setGratitudes(gratitudes);
    admin.setNotification(Notification.builder().user(admin).build());
    notificationRepository.save(admin.getNotification());
    userRepository.save(admin);
    return admin;
  }

  private List<Gratitude> createSampleGratitudes(User user) {
    List<Gratitude> gratitudes = new ArrayList<>();
    gratitudes.add(Gratitude.builder().message("Thank you for your hard work")
        .createdDate(LocalDateTime.now().minusDays(5)).updatedDate(LocalDateTime.now().minusDays(3))
        .user(user).build());
    gratitudes.add(Gratitude.builder().message("Great job on the project!")
        .createdDate(LocalDateTime.now().minusDays(6)).updatedDate(LocalDateTime.now().minusDays(2))
        .user(user).build());
    gratitudes.add(Gratitude.builder().message("Your contributions are highly valued")
        .createdDate(LocalDateTime.now().plusMonths(1))
        .updatedDate(LocalDateTime.now().minusDays(1)).user(user).build());
    gratitudes.add(
        Gratitude.builder().message("Nigga").createdDate(LocalDateTime.now().minusDays(8))
            .updatedDate(LocalDateTime.now().plusMonths(2)).user(user).build());

    return gratitudeRepository.saveAll(gratitudes);
  }

  private String getOrCreateTokenForUser(User user) {
    return tokenRepository.findByUserId(user.getId())
        .map(Token::getToken)
        .orElseGet(() -> {
          String jwt = jwtService.generateToken(user);
          Token token = Token.builder()
              .user(user)
              .token(jwt)
              .tokenType(TokenType.BEARER)
              .build();
          tokenRepository.save(token);
          return jwt;
        });
  }

  public void insertSampleData() {
    List<Habit> habits = new ArrayList<>();

    for (int i = 1; i <= 5; i++) {
      Habit habit = new Habit();
      habit.setTitle("Habit " + i);
      habit.setYearMonth(YearMonth.of(2024, i));
      habit.setTimesPerMonth(10 * i);
      habit.setUserId(i);
      habit.setTrackerId(i);
      habit.setPaletteId(i);

      List<LocalDateTime> coloringTimes = new ArrayList<>();
      coloringTimes.add(LocalDateTime.now().plusDays(i));
      coloringTimes.add(LocalDateTime.now().plusDays(i + 1));
      coloringTimes.add(LocalDateTime.now().plusDays(i + 2));

      habit.setColoringTimes(coloringTimes);

      habits.add(habit);
    }

    habitRepository.saveAll(habits);
  }
}
