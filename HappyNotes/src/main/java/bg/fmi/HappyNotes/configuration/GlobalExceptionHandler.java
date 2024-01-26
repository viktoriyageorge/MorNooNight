package bg.fmi.HappyNotes.configuration;

import bg.fmi.HappyNotes.exceptions.GratitudeException;
import bg.fmi.HappyNotes.exceptions.HabitTrackerException;
import bg.fmi.HappyNotes.exceptions.UserAlreadyExistsException;
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
  public ResponseEntity<String> handleException(UserCredentialsMismatchException e) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<String> handleException(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.IM_USED).body(e.getMessage());
  }

  @ExceptionHandler(GratitudeException.class)
  public ResponseEntity<String> handleException(GratitudeException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(HabitTrackerException.class)
  public ResponseEntity<String> handleException(HabitTrackerException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
