package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.ChangePasswordRequest;
import bg.fmi.HappyNotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PREMIUM_USER')")
public class UserController {
  private final UserService userService;

  @PostMapping("/changePassword")
  public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
    userService.changePassword(request);
    return ResponseEntity.ok().build();
  }
}
