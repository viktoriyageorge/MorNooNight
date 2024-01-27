package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.exceptions.HabitTrackerException;
import bg.fmi.HappyNotes.model.HabitTracker;
import bg.fmi.HappyNotes.repository.HabitTrackerRepository;
import bg.fmi.HappyNotes.service.HabitTrackerService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HabitTrackerServiceImpl implements HabitTrackerService {

  private final HabitTrackerRepository habitTrackerRepository;

  @Override
  public HabitTracker uploadFile(MultipartFile uploadFile) {
    var fileName = uploadFile.getOriginalFilename();
    if (fileName != null && fileName.contains("..")) {
      throw new HabitTrackerException("File name contains invalid path sequence " + fileName);
    }
    try {
      var habitTracker = HabitTracker.builder()
          .name(fileName)
          .type(uploadFile.getContentType())
          .image(uploadFile.getBytes())
          .build();

      return habitTrackerRepository.save(habitTracker);
    } catch (IOException e) {
      throw new HabitTrackerException(
          "Could not store file " + fileName + ". Please try again!" + e);
    }
  }

  @Override
  public HabitTracker getFile(Integer fileId) {
    return habitTrackerRepository.findById(fileId)
        .orElseThrow(() -> new HabitTrackerException("File not found with id " + fileId));
  }

  @Override
  public List<HabitTracker> getAllHabitTrackersCreatedByAdmin() {
    return habitTrackerRepository.findByIsCreatedByAdminTrue();
  }
}
