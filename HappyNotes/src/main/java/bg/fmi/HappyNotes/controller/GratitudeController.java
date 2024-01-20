package bg.fmi.HappyNotes.controller;


import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.model.Gratitude;
import bg.fmi.HappyNotes.service.GratitudeService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
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
public class GratitudeController {
  private final GratitudeService gratitudeService;

  @GetMapping
  public ResponseEntity<List<Gratitude>> getGratitudesBetweenDates(@RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(name = "to") LocalDateTime endDate) {
    return ResponseEntity.ok(gratitudeService.getGratitudesBetweenDates(startDate, endDate));
  }

  @PostMapping("/create")
  public ResponseEntity<Gratitude> createGratitude(@Valid @RequestBody GratitudeDataDTO newGratitude) {
    return ResponseEntity.ok(gratitudeService.createGratitude(newGratitude));
  }

  @PatchMapping("/edit")
  public ResponseEntity<Gratitude> editGratitude(@Valid @RequestBody GratitudeDataDTO gratitudeEditDTO) {
    return ResponseEntity.ok(gratitudeService.editGratitude(gratitudeEditDTO));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteGratitude(@RequestParam(name = "id") Integer id) {
    gratitudeService.deleteGratitude(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/countForToday")
  @PreAuthorize("hasRole('PREMIUM_USER')")
  public ResponseEntity<Integer> getGratitudeCountForToday() {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountForToday());
  }

  @GetMapping("/countForMonth")
  @PreAuthorize("hasRole('PREMIUM_USER')")
  public ResponseEntity<Integer> getGratitudeCountForMonth() {
    return ResponseEntity.ok(gratitudeService.getGratitudeCountForMonth());
  }
}
