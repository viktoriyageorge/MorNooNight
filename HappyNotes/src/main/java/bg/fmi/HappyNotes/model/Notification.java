package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {

  @Id
  @GeneratedValue
  @Column(name = "notification_id")
  private Integer id;

  @Nullable
  private String bedTime;

  private boolean isGratitudeNotificationEnabled;

  @OneToOne
  @JsonBackReference
  private User user;

  @Override
  public String toString() {
    return "Notification{" +
        "id=" + id +
        ", bedTime='" + bedTime + '\'' +
        ", isGratitudeNotificationEnabled=" + isGratitudeNotificationEnabled +
        '}';
  }
}
