package bg.fmi.HappyNotes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GratitudeCountPerMonthDTO {
  private Integer month;
  private Integer count;

  public GratitudeCountPerMonthDTO(Integer month, Integer count) {
    this.month = month;
    this.count = count;
  }
}
