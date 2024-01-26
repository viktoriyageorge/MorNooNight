package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.AuthRequest;
import bg.fmi.HappyNotes.model.AuthResponse;
import bg.fmi.HappyNotes.model.RegisterRequest;

public interface AuthService {
  AuthResponse authenticate(AuthRequest authRequest);

  AuthResponse register(RegisterRequest registerRequest);

  Boolean validateToken(String token);

}
