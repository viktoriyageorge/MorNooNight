package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.ColorPalette;
import java.util.List;

public interface ColorPaletteService {
  ColorPalette createColorPalette(ColorPalette colorPalette);

  ColorPalette updateColorPalette(String primaryColor, String secondaryColor,
      String tertiaryColor, Integer id);

  Boolean deleteColorPalette(Integer id);

  ColorPalette findColorPaletteById(Integer id);

  List<ColorPalette> findAllColorPalettes();
}
