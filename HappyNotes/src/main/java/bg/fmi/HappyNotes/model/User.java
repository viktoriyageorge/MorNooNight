package bg.fmi.HappyNotes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(unique = true)
    private String username;

    private Integer age;

    private String password;

    private String pin;

    private boolean enabled;

    @Builder.Default
    private int jettons = 0;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<Gratitude> gratitudes = Collections.emptyList();

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private Notification notification;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @Builder.Default
    private List<InspirationalQuote> quotes = Collections.emptyList();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", password='[PROTECTED]'" +
                ", pin='[PROTECTED]'" +
                ", enabled=" + enabled +
                ", jettons=" + jettons +
                ", role=" + role +
                ", tokenCount=" + (tokens != null ? tokens.size() : 0) +
                ", gender=" + gender +
                ", gratitudeCount=" + (gratitudes != null ? gratitudes.size() : 0) +
                ", notificationId=" + (notification != null ? notification.getId() : null) +
                ", quotesCount=" + (quotes != null ? quotes.size() : 0) +
                '}';
    }
}
