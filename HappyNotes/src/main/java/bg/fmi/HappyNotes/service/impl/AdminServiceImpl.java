package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.exceptions.UserNotFoundException;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.UserRepository;
import bg.fmi.HappyNotes.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findByRoleIn(List.of(Role.USER, Role.PREMIUM_USER));
  }

  @Override
  @Transactional
  public Boolean enableUser(Integer id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id.toString()));
    user.setEnabled(true);
    userRepository.save(user);
    return true;
  }

  @Override
  @Transactional
  public Boolean disableUser(Integer id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id.toString()));
    user.setEnabled(false);
    userRepository.save(user);
    return true;
  }
}
