package bg.fmi.HappyNotes.repository;

import bg.fmi.HappyNotes.model.ColorPalette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorPaletteRepository extends JpaRepository<ColorPalette, Integer> {

}
