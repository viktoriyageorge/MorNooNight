package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.repository.HabitTrackerRepository;
import bg.fmi.HappyNotes.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CleanupService {

  private final TokenRepository tokenRepository;
  private final HabitTrackerRepository habitTrackerRepository;

  @Scheduled(fixedRate = 60000) // 24 hours in milliseconds
  @Transactional
  public void cleanUp() {
    tokenRepository.deleteAllByExpiredAtIsNotNull();
    habitTrackerRepository.deleteUnreferencedHabitTrackers();
  }

}
