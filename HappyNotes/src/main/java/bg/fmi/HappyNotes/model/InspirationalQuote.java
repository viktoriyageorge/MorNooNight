package bg.fmi.HappyNotes.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class InspirationalQuote {

  private String content;
  private String author;

  private LocalDate dateAdded;;

}
