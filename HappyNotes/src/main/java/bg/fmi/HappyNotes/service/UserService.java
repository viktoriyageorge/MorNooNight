package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.dto.UserDto;
import bg.fmi.HappyNotes.model.ChangePINRequest;
import bg.fmi.HappyNotes.model.ChangePasswordRequest;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.User;

public interface UserService {
  UserDto getUser();

  void changePassword(ChangePasswordRequest request);

  void changePIN(ChangePINRequest request);

  Notification getNotification();

  Notification setBedTime(String bedTime);

  Notification setGratitudeNotificationEnabled(boolean isGratitudeNotificationEnabled);
}
