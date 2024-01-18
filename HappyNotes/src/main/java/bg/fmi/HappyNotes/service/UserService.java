package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.ChangePINRequest;
import bg.fmi.HappyNotes.model.ChangePasswordRequest;

public interface UserService {
  void changePassword(ChangePasswordRequest request);

  void changePIN(ChangePINRequest request);
}
