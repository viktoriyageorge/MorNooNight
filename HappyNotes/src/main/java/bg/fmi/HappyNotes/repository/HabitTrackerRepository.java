package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.HabitTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Integer> {

}
