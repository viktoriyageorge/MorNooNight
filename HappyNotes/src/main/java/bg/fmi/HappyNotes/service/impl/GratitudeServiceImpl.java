package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.dto.GratitudeCountDailyPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeCountPerMonthDTO;
import bg.fmi.HappyNotes.dto.GratitudeDTO;
import bg.fmi.HappyNotes.dto.GratitudeDataDTO;
import bg.fmi.HappyNotes.exceptions.GratitudeException;
import bg.fmi.HappyNotes.model.Gratitude;
import bg.fmi.HappyNotes.model.Notification;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.GratitudeRepository;
import bg.fmi.HappyNotes.repository.InspirationalQuoteRepository;
import bg.fmi.HappyNotes.repository.NotificationRepository;
import bg.fmi.HappyNotes.repository.UserRepository;
import bg.fmi.HappyNotes.service.GratitudeService;
import bg.fmi.HappyNotes.service.QuoteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GratitudeServiceImpl implements GratitudeService {

    private final Logger logger = Logger.getGlobal();
    private final GratitudeRepository gratitudeRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final QuoteService quoteService;
    private final InspirationalQuoteRepository inspirationalQuoteRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public Gratitude createGratitude(GratitudeDataDTO newGratitude) {
        if (newGratitude.getMessage() == null || newGratitude.getMessage().isEmpty()) {
            throw new GratitudeException("Message cannot be empty");
        }

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        var gratitude = Gratitude.builder()
                .message(newGratitude.getMessage())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .user(loggedInUser)
                .build();
        Gratitude savedGratitude = gratitudeRepository.save(gratitude);

        var user = userRepository.findById(loggedInUser.getId()).get();
        if (user.getNotification() == null) {
            Notification notification = new Notification();
            user.setNotification(notificationRepository.save(notification));
            user = userRepository.save(user);
        }
        var bedTime = user.getNotification().getBedTime();

        addJettonsForPremiumUser(user, bedTime);
        addInspirationalQuoteForPremiumUser(user);

        return savedGratitude;
    }

    @Override
    public Gratitude editGratitude(GratitudeDataDTO editedGratitudeInfo) {
        var gratitude = gratitudeRepository.findById(editedGratitudeInfo.getId())
                .orElseThrow(() -> new GratitudeException(
                        "Gratitude with id " + editedGratitudeInfo.getId() + " not found"));

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

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return gratitudeRepository.findByUserIdAndUpdatedDateBetween(loggedInUser.getId(),
                startDate, endDate);
    }

    @Override
    public Integer getGratitudeCountForToday() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.countOfGratitudesForCurrentDay(loggedInUser.getId());
    }

    @Override
    public Integer getGratitudeCountForMonth() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.countGratitudesForTheMonth(loggedInUser.getId());
    }

    public Integer getCountOfGratitudesByUserIdForCurrentYear() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.countGratitudesByUserIdForCurrentYear(loggedInUser.getId());
    }

    @Override
    public Map<Integer, Integer> getGratitudeCountByMonthForCurrentYear(Integer month) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.findGratitudeCountsForEachDayInMonth(month, loggedInUser.getId())
                .stream()
                .collect(
                        Collectors.toMap(
                                GratitudeCountDailyPerMonthDTO::getDay,
                                GratitudeCountDailyPerMonthDTO::getGratitudeCount
                        )
                );
    }

    @Override
    public Map<Integer, Integer> getGratitudeCountByMonthForCurrentYear() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.findGratitudeCountsForEachMonthInCurrentYear(loggedInUser.getId())
                .stream()
                .collect(
                        Collectors.toMap(
                                GratitudeCountPerMonthDTO::getMonth,
                                GratitudeCountPerMonthDTO::getCount
                        )
                );
    }

    @Override
    public List<GratitudeDTO> getRandomGratitudesForUser() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.findRandomGratitudesForUser(loggedInUser.getId());
    }

    @Override
    public List<GratitudeDTO> getTop10LatestGratitudesForUser() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return gratitudeRepository.findTop10ByUserIdOrderByCreatedDateDesc(loggedInUser.getId());
    }

    private void addInspirationalQuoteForPremiumUser(User user) {
        var quotes = user.getQuotes();
        if (user.getRole().equals(Role.PREMIUM_USER)
//                && quotes.stream().noneMatch(quote -> quote.getDateAdded().equals(LocalDate.now()))
        ) {
            var newQuote = quoteService.getUniqueQuoteForUser(user);
            newQuote.setUser(user);
            quotes.add(inspirationalQuoteRepository.save(newQuote));
            user.setQuotes(quotes);
            userRepository.save(user);
            this.sendToWebSocket("/topic/messages", String.format(" \"%s\" - %s", newQuote.getContent(), newQuote.getAuthor()));
        }
    }

    private void addJettonsForPremiumUser(User user, String bedTime) {
        if (bedTime != null && !bedTime.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(bedTime, formatter);
            if (user.getRole().equals(Role.PREMIUM_USER) && time.isBefore(LocalTime.now())
                    && LocalTime.of(23, 59).isAfter(LocalTime.now())) {
                var jettons = user.getJettons();
                user.setJettons(jettons + 1);
                this.sendToWebSocket("/topic/messages", "Received jetton, total jettons: " + userRepository.save(user).getJettons());
            }
        }
    }

    private void sendToWebSocket(String destination, Object payload) {
        logger.info(payload.toString());
        this.messagingTemplate.convertAndSend(destination, payload);
    }
}
