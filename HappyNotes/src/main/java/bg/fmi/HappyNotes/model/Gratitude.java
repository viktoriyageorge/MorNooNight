package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gratitude {

  @Id
  @GeneratedValue
  private Integer id;
  private String message;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;

  @Override
  public String toString() {
    return "Gratitude{" +
        "id=" + id +
        ", message='" + message + '\'' +
        ", createdDate=" + createdDate +
        ", updatedDate=" + updatedDate +
        // User is not included to avoid recursion
        '}';
  }
}
