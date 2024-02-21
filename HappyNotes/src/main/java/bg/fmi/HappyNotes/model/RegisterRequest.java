package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Nonnull
    private String username;
    @Nonnull
    private String password;
    @Nullable
    private String confirmPassword;
    @Nullable
    private Gender gender;
    @Nullable
    private Integer age;
    @Nullable
    private String pin;

    @JsonProperty("isPremium")
    private boolean isPremium;
}
