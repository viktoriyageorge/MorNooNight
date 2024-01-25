package bg.fmi.HappyNotes.model;

import bg.fmi.HappyNotes.utils.YearMonthConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Habit {
  @Id
  @GeneratedValue
  private Integer id;

  private String title;

  @Column(name = "`year_month`", columnDefinition = "DATE")
  @Convert(converter = YearMonthConverter.class)
  private YearMonth yearMonth;

  private Integer timesPerMonth;

  private Integer userId;

  private Integer trackerId;

  private Integer paletteId;

  @ElementCollection
  private List<LocalDateTime> coloringTimes;
}
