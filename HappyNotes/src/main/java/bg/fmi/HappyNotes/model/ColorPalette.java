package bg.fmi.HappyNotes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ColorPalette {
  @Id
  @GeneratedValue
  private Integer id;

  private String primaryColor;
  private String secondaryColor;
  private String tertiaryColor;
}
