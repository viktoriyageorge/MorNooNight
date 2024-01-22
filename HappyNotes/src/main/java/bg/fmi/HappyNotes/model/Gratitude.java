package bg.fmi.HappyNotes.model;

import bg.fmi.HappyNotes.dto.GratitudeCountDailyPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeCountPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
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
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Gratitude.findGratitudeCountsForEachDayInMonth",
        query = "SELECT DAY(g.created_date) AS day, COUNT(*) AS gratitude_count FROM gratitude g WHERE MONTH(g.created_date) = :month AND YEAR(g.created_date) = YEAR(CURRENT_DATE) AND g.user_id = :userId GROUP BY DAY(g.created_date)",
        resultSetMapping = "GratitudeCountMapping"
    ),
    @NamedNativeQuery(
        name = "Gratitude.findGratitudeCountsForEachMonthInCurrentYear",
        query = "SELECT MONTH(g.created_date) AS month, COUNT(*) AS count FROM gratitude g WHERE YEAR(g.created_date) = YEAR(CURRENT_DATE) AND g.user_id = :userId GROUP BY MONTH(g.created_date)",
        resultSetMapping = "GratitudeCountPerMonthMapping"
    ),
    @NamedNativeQuery(
        name = "Gratitude.findRandomGratitudesForUser",
        query = "SELECT g.id AS id, g.message AS message FROM gratitude g WHERE g.user_id = :userId ORDER BY RAND() LIMIT 10",
        resultSetMapping = "RandomGratitudeMapping"
    )
})
@SqlResultSetMappings({
    @SqlResultSetMapping(
        name = "GratitudeCountMapping",
        classes = @ConstructorResult(
            targetClass = GratitudeCountDailyPerMonthDTO.class,
            columns = {
                @ColumnResult(name = "day", type = Integer.class),
                @ColumnResult(name = "gratitude_count", type = Integer.class)
            }
        )
    ),
    @SqlResultSetMapping(
        name = "GratitudeCountPerMonthMapping",
        classes = @ConstructorResult(
            targetClass = GratitudeCountPerMonthDTO.class,
            columns = {
                @ColumnResult(name = "month", type = Integer.class),
                @ColumnResult(name = "count", type = Integer.class)
            }
        )
    ),
    @SqlResultSetMapping(
        name = "RandomGratitudeMapping",
        classes = @ConstructorResult(
            targetClass = GratitudeDTO.class,
            columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "message", type = String.class)
            }
        )
    )
})
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
