import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "InspirationalQuotes")
public class InspirationalQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String quoteText;

    @ManyToMany
    @JoinTable(
            name = "quote_user",
            joinColumns = @JoinColumn(name = "quote_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    // Constructors, getters, and setters
}

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @ManyToMany(mappedBy = "users")
    private List<InspirationalQuote> inspirationalQuotes;

    // Constructors, getters, and setters
}
