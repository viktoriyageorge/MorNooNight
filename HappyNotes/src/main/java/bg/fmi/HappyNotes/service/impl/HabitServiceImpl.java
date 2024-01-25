package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.exceptions.HabitException;
import bg.fmi.HappyNotes.model.Habit;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.HabitRepository;
import bg.fmi.HappyNotes.service.HabitService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

  private final HabitRepository repository;

  @Override
  public List<Habit> getHabits() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return repository.findAllByUserId(user.getId());
  }

  @Override
  public List<Habit> getHabitsForMonth(YearMonth yearMonth) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return repository.findAllByUserIdDate(user.getId(), yearMonth.atDay(1));
  }

  @Override
  public void deleteHabit(Integer habitId) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    repository.deleteByUserIdAndAndId(habitId, user.getId());
  }

  @Override
  public List<Habit> getHabitsForPeriod(YearMonth startMonth, YearMonth endMonth) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return repository.findAllByUserIdAndYearMonthBetween(user.getId(), startMonth, endMonth);
  }

  @Override
  public Habit createHabit(Habit habit) {
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var newHabit = Habit.builder()
        .title(habit.getTitle())
        .timesPerMonth(habit.getTimesPerMonth())
        .yearMonth(habit.getYearMonth())
        .trackerId(habit.getTrackerId())
        .paletteId(habit.getPaletteId())
        .coloringTimes(habit.getColoringTimes())
        .userId(user.getId())
        .build();

    return repository.save(newHabit);
  }

  @Override
  public Habit editHabit(Habit habit, boolean isColored) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var editedHabit = repository
        .findById(habit.getId())
        .orElseThrow(() -> new HabitException("Habit not found"));

    editedHabit.setTitle(habit.getTitle());
    editedHabit.setTimesPerMonth(habit.getTimesPerMonth());
    editedHabit.setYearMonth(habit.getYearMonth());
    editedHabit.setTrackerId(habit.getTrackerId());
    editedHabit.setPaletteId(habit.getPaletteId());

    if (isColored) {
      editedHabit.getColoringTimes().add(LocalDateTime.now());
    }

    return repository.save(editedHabit);
  }

  @Override
  public List<Habit> getHabitsForYear(YearMonth yearMonth) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return repository.findAllByUserIdAndDate(user.getId(), yearMonth.atDay(1));
  }
}
