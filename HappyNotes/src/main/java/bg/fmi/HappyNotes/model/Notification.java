package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notification_id")
    private Integer id;

    @Nullable
    private String bedTime;

    private boolean isGratitudeNotificationEnabled;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
