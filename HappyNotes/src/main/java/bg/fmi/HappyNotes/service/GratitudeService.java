package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.model.Gratitude;
import java.time.LocalDateTime;
import java.util.List;

public interface GratitudeService {
  Gratitude createGratitude(GratitudeDataDTO newGratitude);

  Gratitude editGratitude(GratitudeDataDTO editedGratitudeInfo);

  void deleteGratitude(Integer id);

  List<Gratitude> getGratitudesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

  Integer getGratitudeCountForToday();

  Integer getGratitudeCountForMonth();

}
