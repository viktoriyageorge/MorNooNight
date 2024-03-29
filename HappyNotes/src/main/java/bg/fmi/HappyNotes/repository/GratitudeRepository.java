package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.dto.GratitudeCountDailyPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeCountPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeDTO;
import bg.fmi.HappyNotes.model.Gratitude;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GratitudeRepository extends JpaRepository<Gratitude, Integer> {
  List<Gratitude> findByUserIdAndUpdatedDateBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate);

  @Query(value = "SELECT COUNT(*) FROM gratitude g WHERE DATE(g.created_date) = CURRENT_DATE AND g.user_id = :userId", nativeQuery = true)
  Integer countOfGratitudesForCurrentDay(@Param("userId") Integer userId);

  @Query(value = "SELECT COUNT(*) FROM gratitude g WHERE MONTH(g.created_date) = MONTH(CURRENT_DATE) AND YEAR(g.created_date) = YEAR(CURRENT_DATE) AND g.user_id = :userId", nativeQuery = true)
  Integer countGratitudesForTheMonth(@Param("userId") Integer userId);

  @Query(value = "SELECT COUNT(*) FROM gratitude g WHERE g.user_id = :userId AND YEAR(g.created_date) = YEAR(CURRENT_DATE)", nativeQuery = true)
  Integer countGratitudesByUserIdForCurrentYear(@Param("userId") Integer userId);

  @Query(nativeQuery = true)
  List<GratitudeCountDailyPerMonthDTO> findGratitudeCountsForEachDayInMonth(@Param("month") Integer month, @Param("userId") Integer userId);

  @Query(nativeQuery = true)
  List<GratitudeCountPerMonthDTO> findGratitudeCountsForEachMonthInCurrentYear(@Param("userId") Integer userId);

  @Query(nativeQuery = true)
  List<GratitudeDTO> findRandomGratitudesForUser(@Param("userId") Integer userId);

  List<GratitudeDTO> findTop10ByUserIdOrderByCreatedDateDesc(Integer userId);
}