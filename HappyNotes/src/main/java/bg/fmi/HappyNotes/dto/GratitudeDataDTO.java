package bg.fmi.HappyNotes.dto;


import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class GratitudeDataDTO {
  private String message;
  @Nullable
  private Integer id;
}
