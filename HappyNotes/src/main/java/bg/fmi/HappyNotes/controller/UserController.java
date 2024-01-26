package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.dto.UserDto;
import bg.fmi.HappyNotes.model.ChangePasswordRequest;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "User API, used to manage users. Requires either ADMIN, PREMIUM_USER or USER roles.")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Get user",
      description = "Get the current user",
      tags = {"user", "get", "profile"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned the current user"),
      @ApiResponse(responseCode = "404", description = "User not found"),
  })
  @GetMapping("/profile")
  public ResponseEntity<UserDto> getUser() {
    return ResponseEntity.ok(userService.getUser());
  }

  @Operation(summary = "Change password",
      description = "Change the password of the current user",
      tags = {"user", "post", "changePassword"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Changed the password of the current user"),
      @ApiResponse(responseCode = "406", description = "New password is invalid or the old password is incorrect or both passwords are the same"),
  })
  @PostMapping("/changePassword")
  public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
    userService.changePassword(request);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Get notification",
      description = "Get the notification settings of the current user",
      tags = {"user", "get", "notification"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned the notification settings of the current user")
  })
  @GetMapping("/notification")
  public ResponseEntity<Notification> getNotification() {
    return ResponseEntity.ok(userService.getNotification());
  }

  @Operation(summary = "Set notification",
      description = "Set the notification bed-time hour for the current user",
      tags = {"user", "post", "notification"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Set the notification bed-time hour for the current user")
  })
  @PostMapping("/notification/bedTime")
  public ResponseEntity<Notification> setBedTime(@RequestBody String bedTime) {
    return ResponseEntity.ok(userService.setBedTime(bedTime));
  }

  @Operation(summary = "Set notification",
      description = "Set the notification gratitude notification enabled for the current user",
      tags = {"user", "post", "notification"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Set the notification gratitude notification enabled for the current user")
  })
  @PostMapping("/notification/gratitudeNotificationEnabled")
  public ResponseEntity<Notification> setGratitudeNotificationEnabled(@RequestBody boolean isGratitudeNotificationEnabled) {
    return ResponseEntity.ok(userService.setGratitudeNotificationEnabled(isGratitudeNotificationEnabled));
  }
}
