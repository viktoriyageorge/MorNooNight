package bg.fmi.HappyNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GratitudeDTO {
    private Integer id;
    private String message;
    private LocalDateTime updatedDate;

}
