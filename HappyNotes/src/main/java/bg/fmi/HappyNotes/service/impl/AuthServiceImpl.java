package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.configuration.JwtService;
import bg.fmi.HappyNotes.model.AuthRequest;
import bg.fmi.HappyNotes.model.AuthResponse;
import bg.fmi.HappyNotes.model.Gender;
import bg.fmi.HappyNotes.model.RegisterRequest;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.Token;
import bg.fmi.HappyNotes.model.TokenType;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.TokenRepository;
import bg.fmi.HappyNotes.repository.UserRepository;
import bg.fmi.HappyNotes.service.AuthService;
import java.util.ArrayList;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public AuthResponse authenticate(AuthRequest authRequest) {
    User user = verifyUser(authRequest);
    Optional<String> existingToken = getValidTokenIfExists(user);
    if (existingToken.isPresent()) {
      return AuthResponse.builder().token(existingToken.get()).build();
    }
    String newToken = generateAndSaveToken(user);
    return AuthResponse.builder().token(newToken).build();
  }

  private User verifyUser(AuthRequest authRequest) {
    User user = userRepository.findByUsername(authRequest.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
    if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid password");
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
    Token token = Token
        .builder()
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
      throw new RuntimeException("User with this email already exists");
    }

    User user = createUser(registerRequest);
    userRepository.save(user);
    String jwt = generateAndSaveToken(user);
    return AuthResponse.builder().token(jwt).build();
  }

  private User createUser(RegisterRequest registerRequest) {
    return User.builder()
        .age(registerRequest.getAge())
        .username(registerRequest.getUsername())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .role(Role.USER)
        .pin(passwordEncoder.encode(registerRequest.getPin()))
        .gender(registerRequest.getGender())
        .build();
  }

  public AuthResponse registerAdminUser() {
    User admin = getOrCreateAdminUser();
    String tokenString = getOrCreateTokenForUser(admin);
    List<User> users = new ArrayList<>();
    users.add(new User(null, "user1", 25, "password1", "pin1", true, 100, Role.USER, new ArrayList<>(), Gender.MALE));
    users.add(new User(null, "user2", 30, "password2", "pin2", true, 200, Role.USER, new ArrayList<>(), Gender.FEMALE));
    users.add(new User(null, "user3", 35, "password3", "pin3", true, 300, Role.PREMIUM_USER, new ArrayList<>(), Gender.MALE));
    users.add(new User(null, "user4", 40, "password4", "pin4", true, 400, Role.PREMIUM_USER, new ArrayList<>(), Gender.FEMALE));
    users.add(new User(null, "user5", 45, "password5", "pin5", true, 500, Role.USER, new ArrayList<>(), Gender.MALE));

    // Save the users to the database
    userRepository.saveAll(users);
    return AuthResponse.builder()
        .token(tokenString)
        .build();
  }

  private User getOrCreateAdminUser() {
    return userRepository.findByUsername("admin@mail")
        .orElseGet(() -> {
          User admin = createAdminUser();
          userRepository.save(admin);
          return admin;
        });
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

  private User createAdminUser() {
    return User.builder()
        .username("admin@mail")
        .password(passwordEncoder.encode("admin"))
        .role(Role.ADMIN)
        .build();
  }
}
