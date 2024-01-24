package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.dto.GratitudeDTO;
import bg.fmi.HappyNotes.model.Gratitude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GratitudeService {
  Gratitude createGratitude(GratitudeDataDTO newGratitude);

  Gratitude editGratitude(GratitudeDataDTO editedGratitudeInfo);

  void deleteGratitude(Integer id);

  List<Gratitude> getGratitudesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

  Integer getGratitudeCountForToday();

  Integer getGratitudeCountForMonth();

  Integer getCountOfGratitudesByUserIdForCurrentYear();

  Map<Integer, Integer> getGratitudeCountByMonthForCurrentYear(Integer month);

  Map<Integer, Integer> getGratitudeCountByMonthForCurrentYear();

  List<GratitudeDTO> getRandomGratitudesForUser();

  List<GratitudeDTO> getTop10LatestGratitudesForUser();



}
