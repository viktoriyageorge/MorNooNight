package bg.fmi.HappyNotes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePINRequest {

        private String currentPIN;
        private String newPIN;
        private String confirmationPIN;
}
