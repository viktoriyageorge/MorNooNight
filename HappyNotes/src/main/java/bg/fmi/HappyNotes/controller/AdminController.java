package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manage")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
  }

  @PostMapping("/enable/{id}")
  public ResponseEntity<Boolean> enableUser(@PathVariable Integer id) {
    return ResponseEntity.ok(adminService.enableUser(id));
  }

  @PostMapping("/disable/{id}")
  public ResponseEntity<Boolean> disableUser(@PathVariable Integer id) {
    return ResponseEntity.ok(adminService.disableUser(id));
  }
}
