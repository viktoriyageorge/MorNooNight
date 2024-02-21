package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.AuthRequest;
import bg.fmi.HappyNotes.model.AuthResponse;
import bg.fmi.HappyNotes.model.RegisterRequest;
import bg.fmi.HappyNotes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Authentication API, used to register and authenticate users.")
@CrossOrigin(origins = "https://happy-notes-3rou7st6b-viktoriyageorges-projects.vercel.app")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register user",
            description = "Register user with the given credentials",
            tags = {"auth", "register", "post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully registered user"),
            @ApiResponse(responseCode = "404",
                    description = "Either the username or email is already taken")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid RegisterRequest registerRequest) {

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @Operation(summary = "Authenticate user",
            description = "Authenticate user with the given credentials",
            tags = {"auth", "authenticate", "post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully authenticated user"),
            @ApiResponse(responseCode = "404",
                    description = "Bad credentials")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @Operation(summary = "Validate token",
            description = "Validate the given token",
            tags = {"auth", "validate", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully validated token"),
            @ApiResponse(responseCode = "403",
                    description = "Invalid token")
    })
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader HttpHeaders headers) {
        String authorizationHeader = headers.getFirst("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(false);
        }

        String authToken = authorizationHeader.substring(7);
        boolean isValid = authService.validateToken(authToken);

        return ResponseEntity.ok(isValid);
    }
}
