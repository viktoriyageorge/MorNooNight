package bg.fmi.HappyNotes.controller;


import bg.fmi.HappyNotes.dto.GratitudeDTO;
import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.model.Gratitude;
import bg.fmi.HappyNotes.service.GratitudeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gratitudes")
@Tag(name = "Gratitude Controller", description = "Gratitude API, used to manage user gratitude entries. Requires either PREMIUM_USER or USER roles.")
public class GratitudeController {
  private final GratitudeService gratitudeService;

  @Operation(summary = "Get all gratitudes",
      description = "Get all gratitudes for the current user between the given dates",
      tags = {"gratitudes", "get", "all"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved list of gratitudes for the specified time period."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER', 'ADMIN')")
  @GetMapping
  public ResponseEntity<List<Gratitude>> getGratitudesBetweenDates(@RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(name = "to") LocalDateTime endDate) {
    return ResponseEntity.ok(gratitudeService.getGratitudesBetweenDates(startDate, endDate));
  }

  @Operation(summary = "Create a new gratitude",
      description = "Create a new gratitude for the current user",
      tags = {"gratitudes", "create"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully created a new gratitude."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @PostMapping("/create")
  public ResponseEntity<Gratitude> createGratitude(@Valid @RequestBody GratitudeDataDTO newGratitude) {
    return ResponseEntity.ok(gratitudeService.createGratitude(newGratitude));
  }

  @Operation(summary = "Edit an existing gratitude",
      description = "Edit an existing gratitude for the current user",
      tags = {"gratitudes", "edit"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully edited an existing gratitude."),
      @ApiResponse(responseCode = "404", description = "Gratitude does not exist")
  })
  @PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER', 'ADMIN')")
  @PatchMapping("/edit")
  public ResponseEntity<Gratitude> editGratitude(@Valid @RequestBody GratitudeDataDTO gratitudeEditDTO) {
    return ResponseEntity.ok(gratitudeService.editGratitude(gratitudeEditDTO));
  }

  @Operation(summary = "Delete an existing gratitude",
      description = "Delete an existing gratitude for the current user",
      tags = {"gratitudes", "delete"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted an existing gratitude.")
  })
  @PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER', 'ADMIN')")
  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteGratitude(@RequestParam(name = "id") Integer id) {
    gratitudeService.deleteGratitude(id);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Get gratitude count for today",
      description = "Get gratitude count for today for the current user",
      tags = {"gratitudes", "count", "today"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved gratitude count for today."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/countForToday")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'ADMIN')")
  public ResponseEntity<Integer> getGratitudeCountForToday() {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountForToday());
  }

  @Operation(summary = "Get gratitude count for current month",
      description = "Get gratitude count for current month and user",
      tags = {"gratitudes", "count", "month"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved gratitude count for current month."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/countForMonth")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'ADMIN')")
  public ResponseEntity<Integer> getGratitudeCountForMonth() {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountForMonth());
  }

  @Operation(summary = "Get gratitude count for current year",
      description = "Get gratitude count for current year and user",
      tags = {"gratitudes", "count", "year"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved gratitude count for current year."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/countForYear")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'ADMIN')")
  public ResponseEntity<Integer> getCountOfGratitudesByUserIdForCurrentYear() {
    return ResponseEntity.ok(gratitudeService.getCountOfGratitudesByUserIdForCurrentYear());
  }

  @Operation(summary = "Get gratitude count for specified month in current year",
      description = "Get gratitude count for current year, user and specified month",
      tags = {"gratitudes", "count", "year", "month"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved gratitude count for current year and specified month."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/countByMonthForYear")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'ADMIN')")
  public ResponseEntity<Map<Integer, Integer>> getGratitudeCountByMonthForCurrentYear(@RequestParam(name = "month") Integer month) {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountByMonthForCurrentYear(month));
  }

  @Operation(summary = "Get gratitude count for each month in current year",
      description = "Get gratitude count for current year and user for each month",
      tags = {"gratitudes", "count", "year", "month"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved gratitude count for current year and each month."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/countPerMonthForYear")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'ADMIN')")
  public ResponseEntity<Map<Integer, Integer>> getGratitudeCountByMonthForCurrentYear() {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountByMonthForCurrentYear());
  }

  @Operation(summary = "Get random gratitudes for user",
      description = "Get random gratitudes for user",
      tags = {"gratitudes", "random"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved random gratitudes for user."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/random")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'USER', 'ADMIN')")
  public ResponseEntity<List<GratitudeDTO>> getRandomGratitudesForUser() {
    return ResponseEntity.ok(gratitudeService.getRandomGratitudesForUser());
  }

  @Operation(summary = "Get latest gratitudes for user",
      description = "Get latest gratitudes for user",
      tags = {"gratitudes", "latest"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved latest gratitudes for user."),
      @ApiResponse(responseCode = "403", description = "User is not authorized to access this resource or token is invalid.")
  })
  @GetMapping("/latest")
  @PreAuthorize("hasAnyRole('PREMIUM_USER', 'USER', 'ADMIN')")
  public ResponseEntity<List<GratitudeDTO>> getLatestGratitudes() {
    return ResponseEntity.ok(gratitudeService.getTop10LatestGratitudesForUser());
  }
}
