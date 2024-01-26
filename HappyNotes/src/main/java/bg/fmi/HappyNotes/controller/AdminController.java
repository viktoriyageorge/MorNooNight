package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin Controller", description = "Admin API, used to manage users. Requires ADMIN role.")
public class AdminController {

  private final AdminService adminService;

  @Operation(summary = "Get all users",
      description = "Get all users from the database",
      tags = {"admin", "get", "users"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully retrieved users"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
  }

  @Operation(summary = "Enable user",
      description = "Enable user with the given id",
      tags = {"admin", "enable", "post"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully enabled user"),
      @ApiResponse(responseCode = "404",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @PostMapping("/enable/{id}")
  public ResponseEntity<Boolean> enableUser(@PathVariable Integer id) {
    return ResponseEntity.ok(adminService.enableUser(id));
  }

  @Operation(summary = "Disable user",
      description = "Disable user with the given id",
      tags = {"admin", "disable", "post"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully disabled user"),
      @ApiResponse(responseCode = "404",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @PostMapping("/disable/{id}")
  public ResponseEntity<Boolean> disableUser(@PathVariable Integer id) {
    return ResponseEntity.ok(adminService.disableUser(id));
  }
}
