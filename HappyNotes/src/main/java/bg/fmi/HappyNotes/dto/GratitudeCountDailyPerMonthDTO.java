package bg.fmi.HappyNotes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GratitudeCountDailyPerMonthDTO {

  public GratitudeCountDailyPerMonthDTO(Integer day, Integer gratitudeCount) {
    this.day = day;
    this.gratitudeCount = gratitudeCount;
  }

  private Integer day;
  private Integer gratitudeCount;
}
