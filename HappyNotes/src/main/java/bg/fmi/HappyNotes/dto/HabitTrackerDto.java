package bg.fmi.HappyNotes.dto;

import bg.fmi.HappyNotes.model.HabitTracker;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Optional;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
public class HabitTrackerDto {
    private Integer id;
    private String name;
    private String type;
    private String image;
    private String fileUri;

    /// TODO: remove
    public HabitTrackerDto(HabitTracker habitTracker) {
        this.id = habitTracker.getId();
        this.name = habitTracker.isCreatedByAdmin() ? "Template " + habitTracker.getId() : habitTracker.getName();
        this.type = habitTracker.getType();
        this.image = Base64.getEncoder().encodeToString(habitTracker.getImage());
        this.fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/habitTracker/downloadFile/")
                .path(habitTracker.getId().toString())
                .toUriString();
    }

    /// TODO: Util class
    public static HabitTrackerDto convertFromEntity(HabitTracker habitTracker) {
        return Optional.ofNullable(habitTracker)
                .map(tracker -> HabitTrackerDto.builder()
                        .id(tracker.getId())
                        .name(tracker.getName())
                        .type(tracker.getType())
                        .image(Base64.getEncoder().encodeToString(tracker.getImage()))
                        .fileUri(ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/v1/habitTracker/downloadFile/")
                                .path(habitTracker.getId().toString())
                                .toUriString())
                        .build()).orElse(null);
    }
}
