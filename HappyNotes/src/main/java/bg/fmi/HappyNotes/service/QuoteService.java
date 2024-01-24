package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.InspirationalQuote;
import bg.fmi.HappyNotes.model.User;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class QuoteService {

  private static final String QUOTE_API_URL = "https://api.quotable.io/quotes/random?tags=inspirational";

  private final WebClient webClient;

  public QuoteService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl(QUOTE_API_URL).build();
  }

  public InspirationalQuote getUniqueQuoteForUser(User user) {
    InspirationalQuote newQuote;
    boolean isUnique;

    do {
      newQuote = getQuote(); // Fetch a new quote
      isUnique = isQuoteUniqueForUser(newQuote, user);
    } while (!isUnique);

    return newQuote;
  }

  private boolean isQuoteUniqueForUser(InspirationalQuote quote, User user) {
    return user.getQuotes().stream()
        .noneMatch(existingQuote -> existingQuote.getContent().equals(quote.getContent())
            && existingQuote.getAuthor().equals(quote.getAuthor()));
  }

  public InspirationalQuote getQuote() {
    return webClient.get()
        .retrieve()
        .bodyToFlux(InspirationalQuote.class) // Use bodyToFlux instead of bodyToMono
        .next() // Take the first InspirationalQuote object
        .map(quote -> {
          quote.setDateAdded(LocalDate.now());
          return quote;
        })
        .block();
  }
}
