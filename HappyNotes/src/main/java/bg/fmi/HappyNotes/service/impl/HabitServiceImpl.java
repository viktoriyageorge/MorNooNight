package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.exceptions.HabitException;
import bg.fmi.HappyNotes.model.Habit;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.HabitRepository;
import bg.fmi.HappyNotes.service.HabitService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteHabit(Integer habitId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        repository.deleteByUserIdAndAndId(user.getId(), habitId);
    }

    @Override
    public List<Habit> getHabitsForPeriod(YearMonth startMonth, YearMonth endMonth) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return repository.findAllByUserIdAndYearMonthBetween(user.getId(), startMonth, endMonth);
    }

    @Override
    @Transactional
    public Habit createHabit(Habit habit) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var newHabit = Habit.builder()
                .title(habit.getTitle())
                .timesPerMonth(habit.getTimesPerMonth())
                .yearMonth(habit.getYearMonth())
                .trackerId(habit.getTrackerId())
                .paletteId(habit.getPaletteId())
                .segmentsJson(habit.getSegmentsJson())
                .coloringTimes(habit.getColoringTimes())
                .userId(user.getId())
                .build();

        return repository.save(newHabit);
    }

    @Override
    @Transactional
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
        editedHabit.setSegmentsJson(habit.getSegmentsJson());

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

    @Override
    public Boolean getIsHabitCompletedForToday(Integer habitId) {
        Habit habit = repository.findById(habitId).orElse(Habit.builder().timesPerMonth(0).coloringTimes(new ArrayList<>()).build());

        if(habit.getTimesPerMonth().compareTo(habit.getYearMonth().lengthOfMonth()) < 1){
            return habit.getColoringTimes().stream().anyMatch(date -> date.toLocalDate().equals(LocalDate.now()));
        }
        return habit.getSegmentsJson().isBlank() || habit.getSegmentsJson().equals("[]");
    }
}
