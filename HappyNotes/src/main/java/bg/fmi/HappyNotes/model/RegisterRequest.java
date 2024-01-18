package bg.fmi.HappyNotes.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    @Nonnull
    private String username;
    @Nonnull
    private String password;
    @Nullable
    private Gender gender;
    @Nullable
    private Integer age;
    @Nullable
    private String pin;
}
