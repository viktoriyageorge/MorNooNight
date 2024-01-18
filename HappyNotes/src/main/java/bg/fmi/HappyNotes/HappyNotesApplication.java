package bg.fmi.HappyNotes;


import bg.fmi.HappyNotes.service.impl.AuthServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HappyNotesApplication {

  public static void main(String[] args) {
    SpringApplication.run(HappyNotesApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AuthServiceImpl service) {
    return args -> {
      System.out.println("Admin token: " + service.registerAdminUser()
          .getToken());
    };
  }
}