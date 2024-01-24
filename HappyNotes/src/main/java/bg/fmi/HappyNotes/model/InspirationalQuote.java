package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
public class InspirationalQuote {

  @Id
  @GeneratedValue
  private Integer id;

  private String content;
  private String author;

  private LocalDate dateAdded;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @Override
  public String toString() {
    return "InspirationalQuote{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", author='" + author + '\'' +
        ", dateAdded=" + dateAdded +
        '}';
  }

}
