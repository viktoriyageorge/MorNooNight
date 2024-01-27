package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.Habit;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {
  List<Habit> findAllByUserId(Integer userId);

  @Query(value = "SELECT * FROM habit WHERE user_id = :userId AND MONTH(`year_month`) = MONTH(:monthValue) AND YEAR(`year_month`) = YEAR(CURRENT_DATE)", nativeQuery = true)
  List<Habit> findAllByUserIdDate(Integer userId, LocalDate monthValue);

  void deleteByUserIdAndAndId(Integer userId, Integer habitId);

  List<Habit> findAllByUserIdAndYearMonthBetween(Integer userId, YearMonth startMonth, YearMonth endMonth);

  @Query(value = "SELECT * FROM habit WHERE user_id = :userId AND YEAR(`year_month`) = YEAR(:yearMonth)", nativeQuery = true)
  List<Habit> findAllByUserIdAndDate(Integer userId, LocalDate yearMonth);

  @Query(value = "SELECT tracker_id FROM habit", nativeQuery = true)
  List<Integer> getUsedTrackerIds();
}
