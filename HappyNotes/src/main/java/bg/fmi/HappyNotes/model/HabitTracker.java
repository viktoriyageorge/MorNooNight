package bg.fmi.HappyNotes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HabitTracker {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  private String type;

  @Lob
  @Column(columnDefinition="LONGBLOB")
  byte[] image;

  private boolean isCreatedByAdmin;
}
