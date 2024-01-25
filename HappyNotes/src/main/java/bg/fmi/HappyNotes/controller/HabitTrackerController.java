package bg.fmi.HappyNotes.controller;

import bg.fmi.HappyNotes.model.UploadFileResponse;
import bg.fmi.HappyNotes.service.HabitTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Ако админ го качва - да се направи специален лист
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/habitTracker")
public class HabitTrackerController {

  private final HabitTrackerService service;

  @PostMapping("/uploadFile")
  public ResponseEntity<UploadFileResponse> fileUpload(@RequestBody MultipartFile file) {
    var habitTracker = service.uploadFile(file);

    var fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/v1/habitTracker/downloadFile/")
        .path(habitTracker.getId().toString())
        .toUriString();

    return ResponseEntity.ok(
        new UploadFileResponse(habitTracker.getName(), fileUri, file.getContentType(),
            file.getSize()));

  }

  @GetMapping("/downloadFile/{fileId}")
  public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
    var habitTracker = service.getFile(fileId);

    return ResponseEntity.ok()
        .contentType(org.springframework.http.MediaType.parseMediaType(habitTracker.getType()))
        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + habitTracker.getName() + "\"")
        .body(new ByteArrayResource(habitTracker.getImage()));
  }
}
