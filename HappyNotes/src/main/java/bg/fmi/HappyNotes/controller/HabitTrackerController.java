package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.dto.HabitTrackerDto;
import bg.fmi.HappyNotes.model.HabitTracker;
import bg.fmi.HappyNotes.model.UploadFileResponse;
import bg.fmi.HappyNotes.service.HabitTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/habitTracker")
@PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER', 'ADMIN')")
@Tag(name = "Habit Tracker Controller", description = "Habit Tracker API, where functionality for uploading/downloading files is accessed. Requires USER or PREMIUM_USER role.")
public class HabitTrackerController {

    private final HabitTrackerService service;

    @Operation(summary = "Upload file",
            description = "Upload attachment for habit tracker",
            tags = {"habitTracker", "upload", "post"})
    @PostMapping("/uploadFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully uploaded attachment for habit tracker"),
            @ApiResponse(responseCode = "404",
                    description = "Either attached filename has invalid characters or the file is empty")
    })
    public ResponseEntity<Integer> fileUpload(@RequestBody MultipartFile file) {
        var habitTracker = service.uploadFile(file);

//        var fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/v1/habitTracker/downloadFile/")
//                .path(habitTracker.getId().toString())
//                .toUriString();

        return ResponseEntity.ok(habitTracker.getId());

    }

    @Operation(summary = "Download file",
            description = "Download attachment for habit tracker",
            tags = {"habitTracker", "download", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully downloaded attachment for habit tracker"),
            @ApiResponse(responseCode = "404",
                    description = "File with given id does not exist")
    })
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
        var habitTracker = service.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(habitTracker.getType()))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + habitTracker.getName() + "\"")
                .body(new ByteArrayResource(habitTracker.getImage()));
    }

    @GetMapping("/getAllCreatedByAdmin")
    public ResponseEntity<List<HabitTrackerDto>> getAllHabitTrackersCreatedByAdmin() {
        return ResponseEntity.ok(service.getAllHabitTrackersCreatedByAdmin());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitTrackerDto> getHabitTrackerById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getHabitTrackerById(id));
    }
}
