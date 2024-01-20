package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.exceptions.GratitudeException;
import bg.fmi.HappyNotes.model.Gratitude;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.GratitudeRepository;
import bg.fmi.HappyNotes.service.GratitudeService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GratitudeServiceImpl implements GratitudeService {

  private final GratitudeRepository gratitudeRepository;

  @Override
  public Gratitude createGratitude(GratitudeDataDTO newGratitude) {
    if(newGratitude.getMessage() == null || newGratitude.getMessage().isEmpty()) {
      throw new GratitudeException("Message cannot be empty");
    }

    User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var gratitude = Gratitude.builder()
        .message(newGratitude.getMessage())
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .user(loggedInUser)
        .build();

    return gratitudeRepository.save(gratitude);
  }

  @Override
  public Gratitude editGratitude(GratitudeDataDTO editedGratitudeInfo) {
    var gratitude = gratitudeRepository.findById(editedGratitudeInfo.getId())
        .orElseThrow(() -> new GratitudeException("Gratitude with id " + editedGratitudeInfo.getId() + " not found"));

    gratitude.setMessage(editedGratitudeInfo.getMessage());
    gratitude.setUpdatedDate(LocalDateTime.now());

    return gratitudeRepository.save(gratitude);
  }

  @Override
  public void deleteGratitude(Integer id) {
    gratitudeRepository.deleteById(id);
  }

  @Override
  public List<Gratitude> getGratitudesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {

    User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var test = gratitudeRepository.findByUserIdAndUpdatedDateBetween(loggedInUser.getId(), startDate, endDate);

    return test;
  }
}
