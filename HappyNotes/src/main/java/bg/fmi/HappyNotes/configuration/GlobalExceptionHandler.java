package bg.fmi.HappyNotes.configuration;

import bg.fmi.HappyNotes.exceptions.UserNotFoundException;
import bg.fmi.HappyNotes.exceptions.UserCredentialsMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(UserCredentialsMismatchException.class)
  public ResponseEntity<?> handleException(UserCredentialsMismatchException e) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
  }
}
