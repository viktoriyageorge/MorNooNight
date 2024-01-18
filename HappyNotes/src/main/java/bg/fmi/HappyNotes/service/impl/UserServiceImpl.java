package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.exceptions.UserCredentialsMismatchException;
import bg.fmi.HappyNotes.model.ChangePINRequest;
import bg.fmi.HappyNotes.model.ChangePasswordRequest;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.UserRepository;
import bg.fmi.HappyNotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void changePassword(ChangePasswordRequest request) {

    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(!passwordEncoder.matches(request.getCurrentPassword(), loggedUser.getPassword())) {
      throw new UserCredentialsMismatchException("Old password does not match");
    }

    if(!request.getNewPassword().equals(request.getConfirmationPassword())) {
      throw new UserCredentialsMismatchException("New password does not match");
    }

    loggedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

    userRepository.save(loggedUser);

  }

  @Override
  public void changePIN(ChangePINRequest request) {

      User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      if(!passwordEncoder.matches(request.getCurrentPIN(), loggedUser.getPin())) {
        throw new UserCredentialsMismatchException("Old PIN does not match");
      }

      if(!request.getNewPIN().equals(request.getConfirmationPIN())) {
        throw new UserCredentialsMismatchException("New PIN does not match");
      }

      loggedUser.setPin(passwordEncoder.encode(request.getNewPIN()));

      userRepository.save(loggedUser);
  }
}
