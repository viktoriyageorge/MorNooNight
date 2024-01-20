package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.Gratitude;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GratitudeRepository extends JpaRepository<Gratitude, Integer> {
  List<Gratitude> findByUserIdAndUpdatedDateBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate);

  @Query(value = "SELECT COUNT(*) FROM gratitude g WHERE DATE(g.created_date) = CURRENT_DATE", nativeQuery = true)
  Integer countOfGratitudesForCurrentDay();
}
