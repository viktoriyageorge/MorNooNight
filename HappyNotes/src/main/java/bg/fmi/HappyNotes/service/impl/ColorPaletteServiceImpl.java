package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.model.ColorPalette;
import bg.fmi.HappyNotes.repository.ColorPaletteRepository;
import bg.fmi.HappyNotes.service.ColorPaletteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorPaletteServiceImpl implements ColorPaletteService {

  private final ColorPaletteRepository repository;

  @Override
  public ColorPalette createColorPalette(ColorPalette colorPalette) {
    var newColorPalette = ColorPalette.builder()
        .primaryColor(colorPalette.getPrimaryColor())
        .secondaryColor(colorPalette.getSecondaryColor())
        .tertiaryColor(colorPalette.getTertiaryColor())
        .build();

    return repository.save(newColorPalette);
  }

  @Override
  public ColorPalette updateColorPalette(String primaryColor, String secondaryColor,
      String tertiaryColor, Integer id) {
    var colorPalette = repository.findById(id).orElseThrow();
    colorPalette.setPrimaryColor(primaryColor);
    colorPalette.setSecondaryColor(secondaryColor);
    colorPalette.setTertiaryColor(tertiaryColor);

    return repository.save(colorPalette);
  }

  @Override
  public Boolean deleteColorPalette(Integer id) {
    return repository.findById(id).map(colorPalette -> {
      repository.delete(colorPalette);
      return true;
    }).orElse(false);
  }

  @Override
  public ColorPalette findColorPaletteById(Integer id) {
    return repository.findById(id).orElseThrow();
  }

  @Override
  public List<ColorPalette> findAllColorPalettes() {
    return repository.findAll();
  }
}
