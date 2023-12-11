# MorNooNight

User roles:
1. Admin(one for the system, presetted) - managing users and resources* in the app
2. User(everyone can register(create user) in the system) - managing own content**
3. Premium user(manually entered into the database, has the same rights and permissions as the user and enhances it with additional access) - managing own content + reading ´special’ content**


*resources - preset templates which user is going to be able to attach and enrich(for example the multimedia/board which is going to be ´colored’ in their habbits)

**content - tables managed by users and stored in database(for example habbits, thanks)

***´special’ content - manually filled tables in the database(for example some multimedia/text)


![image](https://github.com/viktoriyageorge/MorNooNight/assets/73623633/efa62d5a-215d-48a4-a6aa-8f82695c1eed)
 
*Note: Brief example for implementing roles with Spring Security*

// UserDetailsService implementation

public class CustomUserDetails implements UserDetails {

    private CustomUser user;

    public CustomUserDetails(CustomUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    // ... other UserDetails methods
}


// Security configuration

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



