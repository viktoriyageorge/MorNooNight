package bg.fmi.HappyNotes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GratitudeDTO {
  private Integer id;
  private String message;

  public GratitudeDTO(Integer id, String message) {
    this.id = id;
    this.message = message;
  }
}
