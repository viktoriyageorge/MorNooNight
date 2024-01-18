package bg.fmi.HappyNotes.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
      super("User with given id: " +message+ " not found");
    }
}
