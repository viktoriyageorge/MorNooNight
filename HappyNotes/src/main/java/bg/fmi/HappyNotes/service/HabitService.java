package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.Habit;
import java.time.YearMonth;
import java.util.List;

public interface HabitService {

  List<Habit> getHabits();

  List<Habit> getHabitsForMonth(YearMonth yearMonth);

  void deleteHabit(Integer habitId);

  List<Habit> getHabitsForPeriod(YearMonth startMonth, YearMonth endMonth);

  Habit createHabit(Habit habit);

  Habit editHabit(Habit habit, boolean isColored);

  List<Habit> getHabitsForYear(YearMonth yearMonth);
}
