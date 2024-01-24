package bg.fmi.HappyNotes.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
