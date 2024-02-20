package bg.fmi.HappyNotes.configuration;

import bg.fmi.HappyNotes.model.Token;
import bg.fmi.HappyNotes.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      response.setStatus(
          HttpServletResponse.SC_BAD_REQUEST); // Set response status to 400 Bad Request
      return;
    }
    final String jwt = authHeader.substring(7);

    Token storedToken = tokenRepository.findByToken(jwt).stream().findFirst().orElse(null);

    if (storedToken != null) {
      storedToken.setExpiredAt(LocalDateTime.now());
      tokenRepository.save(storedToken);
    }
    SecurityContextHolder.clearContext();
  }
}

