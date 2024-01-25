package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.Habit;
import bg.fmi.HappyNotes.service.HabitService;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/habit")
@RequiredArgsConstructor
public class HabitController {

  private final HabitService service;

  @GetMapping("/all")
  public ResponseEntity<List<Habit>> findAllHabits() {
    return ResponseEntity.ok(service.getHabits());
  }

  @PostMapping("/create")
  public ResponseEntity<Habit> createHabit(@RequestBody Habit habit) {
    return ResponseEntity.ok(service.createHabit(habit));
  }

  @PutMapping("/edit")
  public ResponseEntity<Habit> editHabit(@RequestBody Habit habit,@RequestParam(name = "isColored") boolean isColored) {
    return ResponseEntity.ok(service.editHabit(habit, isColored));
  }

  @DeleteMapping("/delete/{habitId}")
  public ResponseEntity<Void> deleteHabit(@PathVariable Integer habitId) {
    service.deleteHabit(habitId);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/month")
  public ResponseEntity<List<Habit>> getHabitsForMonth(@RequestParam(name = "date") YearMonth yearMonth) {
    return ResponseEntity.ok(service.getHabitsForMonth(yearMonth));
  }

  @GetMapping("/year")
  public ResponseEntity<List<Habit>> getHabitsForYear(@RequestParam(name = "date") YearMonth yearMonth) {
    return ResponseEntity.ok(service.getHabitsForYear(yearMonth));
  }

  @GetMapping("/period")
  public ResponseEntity<List<Habit>> getHabitsForPeriod(
      @RequestParam(name = "start") YearMonth startMonth,
      @RequestParam(name = "end") YearMonth endMonth
  ) {
    return ResponseEntity.ok(service.getHabitsForPeriod(startMonth, endMonth));
  }
}
