package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.HabitTracker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Integer> {
  List<HabitTracker> findByIsCreatedByAdminTrue();

  @Query(value = "SELECT id FROM habit_tracker WHERE is_created_by_admin = 0", nativeQuery = true)
  List<Integer> findByIsCreatedByAdminFalse();

  @Modifying
  @Query(value = "DELETE FROM habit_tracker WHERE is_created_by_admin = FALSE AND NOT EXISTS (SELECT 1 FROM habit WHERE habit.tracker_id = habit_tracker.id)", nativeQuery = true)
  void deleteUnreferencedHabitTrackers();
}
