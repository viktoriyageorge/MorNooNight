package bg.fmi.HappyNotes.service;

import bg.fmi.HappyNotes.model.InspirationalQuote;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class QuoteService {

  private static final String QUOTE_API_URL = "https://api.quotable.io/quotes/random?tags=inspirational";

  private final WebClient webClient;

  public QuoteService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl(QUOTE_API_URL).build();
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
