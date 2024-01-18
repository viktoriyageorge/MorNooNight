package bg.fmi.HappyNotes.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/demo")
public class DemoController {


      @GetMapping("/hello")
      public String hello() {
          return "Hello World!";
      }

      @PostMapping("/user")
      public String postTest() {
        return "POST:: user ROLE";
      }

}
