package bg.fmi.HappyNotes.service.impl;

import bg.fmi.HappyNotes.dto.HabitTrackerDto;
import bg.fmi.HappyNotes.exceptions.HabitTrackerException;
import bg.fmi.HappyNotes.model.HabitTracker;
import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.model.User;
import bg.fmi.HappyNotes.repository.HabitTrackerRepository;
import bg.fmi.HappyNotes.service.HabitTrackerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HabitTrackerServiceImpl implements HabitTrackerService {

    private final HabitTrackerRepository habitTrackerRepository;

    @Override
    public HabitTracker uploadFile(MultipartFile uploadFile) {
        var fileName = uploadFile.getOriginalFilename();
        if (fileName != null && fileName.contains("..")) {
            throw new HabitTrackerException("File name contains invalid path sequence " + fileName);
        }
        try {
            var habitTracker = HabitTracker.builder()
                    .name(fileName)
                    .type(uploadFile.getContentType())
                    .image(uploadFile.getBytes())
                    .isCreatedByAdmin(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                            .getRole().equals(Role.ADMIN))
                    .build();

            return habitTrackerRepository.save(habitTracker);
        } catch (IOException e) {
            throw new HabitTrackerException(
                    "Could not store file " + fileName + ". Please try again!" + e);
        }
    }

    @Override
    public HabitTracker getFile(Integer fileId) {
        return habitTrackerRepository.findById(fileId)
                .orElseThrow(() -> new HabitTrackerException("File not found with id " + fileId));
    }

    @Override
    public List<HabitTrackerDto> getAllHabitTrackersCreatedByAdmin() {
        return habitTrackerRepository.findByIsCreatedByAdminTrue().stream().map(HabitTrackerDto::convertFromEntity).collect(Collectors.toList());
    }

    @Override
    public HabitTrackerDto getHabitTrackerById(Integer id) {
        return HabitTrackerDto.convertFromEntity(habitTrackerRepository.findById(id).orElse(null));
    }


}
