package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.InspirationalQuote;
import bg.fmi.HappyNotes.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

  private final QuoteService quoteService;


      @GetMapping("/hello")
      public String hello() {
          return "Hello World!";
      }

      @PostMapping("/user")
      public String postTest() {
        return "POST:: user ROLE";
      }

      @GetMapping("/testQuote")
      public ResponseEntity<InspirationalQuote> getQuote() {
        return ResponseEntity.ok(quoteService.getQuote());
      }
}
