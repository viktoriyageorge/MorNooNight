package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token implements Comparable<Token> {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "Token{" + "id=" + id + ", token='" + token + '\'' + ", tokenType=" + tokenType + ", expiredAt=" + expiredAt +
                // User is not included to avoid recursion
                '}';
    }

    @Override
    public int compareTo(Token o) {
        return this.getExpiredAt().compareTo(o.getExpiredAt());
    }
}

