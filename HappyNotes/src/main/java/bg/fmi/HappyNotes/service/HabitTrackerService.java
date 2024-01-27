package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.HabitTracker;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface HabitTrackerService {
  HabitTracker uploadFile(MultipartFile uploadFile);

  HabitTracker getFile(Integer fileId);

  List<HabitTracker> getAllHabitTrackersCreatedByAdmin();
}
