package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.User;
import java.util.List;

public interface AdminService {
  List<User> getAllUsers();

  Boolean enableUser(Integer id);

  Boolean disableUser(Integer id);
}
