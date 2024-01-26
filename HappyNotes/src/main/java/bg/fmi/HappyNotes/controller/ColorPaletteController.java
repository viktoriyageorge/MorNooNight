package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.ColorPalette;
import bg.fmi.HappyNotes.service.ColorPaletteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Color Palette Controller", description = "Color Palette API, used to manage color palettes. Requires ADMIN role.")
public class ColorPaletteController {

  private final ColorPaletteService service;

  @Operation(summary = "Create color palette",
      description = "Create color palette with the given colors",
      tags = {"color-palette", "create", "post"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully created color palette"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ColorPalette> createColorPalette(@RequestBody ColorPalette colorPalette) {
    return ResponseEntity.ok(service.createColorPalette(colorPalette));
  }

  @Operation(summary = "Update color palette",
      description = "Update color palette with the given colors",
      tags = {"color-palette", "update", "put"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully updated color palette"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @PutMapping("/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ColorPalette> updateColorPalette(@RequestBody ColorPalette colorPalette) {
    return ResponseEntity.ok(
        service.updateColorPalette(colorPalette.getPrimaryColor(), colorPalette.getSecondaryColor(),
            colorPalette.getTertiaryColor(), colorPalette.getId()));
  }

  @Operation(summary = "Delete color palette",
      description = "Delete color palette with the given id",
      tags = {"color-palette", "delete", "delete"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully deleted color palette"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Boolean> deleteColorPalette(@PathVariable Integer id) {
    return ResponseEntity.ok(service.deleteColorPalette(id));
  }

  @Operation(summary = "Find color palette by id",
      description = "Find color palette with the given id",
      tags = {"color-palette", "find", "get"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully found color palette"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @GetMapping("/find/{id}")
  public ResponseEntity<ColorPalette> findColorPaletteById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findColorPaletteById(id));
  }

  @Operation(summary = "Find all color palettes",
      description = "Find all color palettes",
      tags = {"color-palette", "find", "get"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Successfully found all color palettes"),
      @ApiResponse(responseCode = "403",
          description = "Either the user is not authenticated or does not have the required role")
  })
  @GetMapping("/allPalettes")
  public ResponseEntity<List<ColorPalette>> findAllColorPalettes() {
    return ResponseEntity.ok(service.findAllColorPalettes());
  }
}