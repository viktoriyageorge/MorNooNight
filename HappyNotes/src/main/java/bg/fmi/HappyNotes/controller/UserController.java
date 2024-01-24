package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.dto.UserDto;
import bg.fmi.HappyNotes.model.ChangePasswordRequest;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/profile")
  public ResponseEntity<UserDto> getUser() {
    return ResponseEntity.ok(userService.getUser());
  }

  @PostMapping("/changePassword")
  public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
    userService.changePassword(request);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/notification")
  public ResponseEntity<Notification> getNotification() {
    return ResponseEntity.ok(userService.getNotification());
  }

  @PostMapping("/notification/bedTime")
  public ResponseEntity<Notification> setBedTime(@RequestBody String bedTime) {
    return ResponseEntity.ok(userService.setBedTime(bedTime));
  }

  @PostMapping("/notification/gratitudeNotificationEnabled")
  public ResponseEntity<Notification> setGratitudeNotificationEnabled(@RequestBody boolean isGratitudeNotificationEnabled) {
    return ResponseEntity.ok(userService.setGratitudeNotificationEnabled(isGratitudeNotificationEnabled));
  }
}
