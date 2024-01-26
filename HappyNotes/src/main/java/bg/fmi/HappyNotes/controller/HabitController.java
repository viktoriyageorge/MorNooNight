package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.Habit;
import bg.fmi.HappyNotes.service.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER')")
@Tag(name = "Habit Controller", description = "Habit API, used to manage user habits. Requires either PREMIUM_USER or USER roles.")
public class HabitController {

  private final HabitService service;

  @Operation(summary = "Get all habits",
      description = "Get all habits for the current user",
      tags = {"habits", "get", "all"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned all habits"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token"),
  })
  @GetMapping("/all")
  public ResponseEntity<List<Habit>> findAllHabits() {
    return ResponseEntity.ok(service.getHabits());
  }

  @Operation(summary = "Create habit",
      description = "Create a new habit for the current user",
      tags = {"habits", "post", "id"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created a new habit"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token"),
  })
  @PostMapping("/create")
  public ResponseEntity<Habit> createHabit(@RequestBody Habit habit) {
    return ResponseEntity.ok(service.createHabit(habit));
  }

  @Operation(summary = "Edit habit",
      description = "Edit an existing habit for the current user. Also, adds timestamp if the habit is colored.",
      tags = {"habits", "put", "id"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Edited an existing habit"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token"),
  })
  @PutMapping("/edit")
  public ResponseEntity<Habit> editHabit(@RequestBody Habit habit,@RequestParam(name = "isColored") boolean isColored) {
    return ResponseEntity.ok(service.editHabit(habit, isColored));
  }

  @Operation(summary = "Delete habit",
      description = "Delete an existing habit by id for the current user",
      tags = {"habits", "delete", "id"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted an existing habit"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token"),
  })
  @DeleteMapping("/delete/{habitId}")
  public ResponseEntity<Void> deleteHabit(@PathVariable Integer habitId) {
    service.deleteHabit(habitId);
    return ResponseEntity.accepted().build();
  }

  @Operation(summary = "Get habits for month",
      description = "Get all habits for the current user for a given month",
      tags = {"habits", "get", "month"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned all habits for a given month"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token")
  })
  @GetMapping("/month")
  public ResponseEntity<List<Habit>> getHabitsForMonth(@RequestParam(name = "date") YearMonth yearMonth) {
    return ResponseEntity.ok(service.getHabitsForMonth(yearMonth));
  }

  @Operation(summary = "Get habits for year",
      description = "Get all habits for the current user for a given year",
      tags = {"habits", "get", "year"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned all habits for a given year"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token")
  })
  @GetMapping("/year")
  public ResponseEntity<List<Habit>> getHabitsForYear(@RequestParam(name = "date") YearMonth yearMonth) {
    return ResponseEntity.ok(service.getHabitsForYear(yearMonth));
  }

  @Operation(summary = "Get habits for period",
      description = "Get all habits for the current user for a given period",
      tags = {"habits", "get", "period"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Returned all habits for a given period"),
      @ApiResponse(responseCode = "403", description = "Invalid JWT token")
  })
  @GetMapping("/period")
  public ResponseEntity<List<Habit>> getHabitsForPeriod(
      @RequestParam(name = "start") YearMonth startMonth,
      @RequestParam(name = "end") YearMonth endMonth
  ) {
    return ResponseEntity.ok(service.getHabitsForPeriod(startMonth, endMonth));
  }
}
