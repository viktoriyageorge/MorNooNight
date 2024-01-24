package bg.fmi.HappyNotes.dto;

import bg.fmi.HappyNotes.model.Gender;
import bg.fmi.HappyNotes.model.InspirationalQuote;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private String username;
  private Integer age;
  private Integer jettons;
  private Role role;
  private Gender gender;
  private Notification notification;
  private List<InspirationalQuote> quotes;

  public static UserDto fromUser(User user){
    return UserDto.builder()
        .username(user.getUsername())
        .age(user.getAge())
        .jettons(user.getJettons())
        .role(user.getRole())
        .gender(user.getGender())
        .notification(user.getNotification())
        .quotes(user.getQuotes())
        .build();
  }

}
