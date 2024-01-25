package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.ColorPalette;
import bg.fmi.HappyNotes.service.ColorPaletteService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/color-palettes")
@RequiredArgsConstructor
public class ColorPaletteController {

  private final ColorPaletteService service;

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ColorPalette> createColorPalette(@RequestBody ColorPalette colorPalette) {
    return ResponseEntity.ok(service.createColorPalette(colorPalette));
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ColorPalette> updateColorPalette(@RequestBody ColorPalette colorPalette) {
    return ResponseEntity.ok(
        service.updateColorPalette(colorPalette.getPrimaryColor(), colorPalette.getSecondaryColor(),
            colorPalette.getTertiaryColor(), colorPalette.getId()));
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Boolean> deleteColorPalette(@PathVariable Integer id) {
    return ResponseEntity.ok(service.deleteColorPalette(id));
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<ColorPalette> findColorPaletteById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findColorPaletteById(id));
  }

  @GetMapping("/allPalettes")
  public ResponseEntity<List<ColorPalette>> findAllColorPalettes() {
    return ResponseEntity.ok(service.findAllColorPalettes());
  }
}