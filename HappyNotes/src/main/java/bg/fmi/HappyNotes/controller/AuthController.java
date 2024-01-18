package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.AuthRequest;
import bg.fmi.HappyNotes.model.AuthResponse;
import bg.fmi.HappyNotes.model.RegisterRequest;
import bg.fmi.HappyNotes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {

    return ResponseEntity.ok(authService.register(registerRequest));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest authRequest) {
    return ResponseEntity.ok(authService.authenticate(authRequest));
  }
}
