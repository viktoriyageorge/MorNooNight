package bg.fmi.HappyNotes.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import bg.fmi.HappyNotes.model.Role;
import bg.fmi.HappyNotes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

  private final UserRepository userRepository;
  private final LogoutService logoutLogic;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
      JwtAuthenticationFilter jwtAuthFilter) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> {
          req.requestMatchers("/api/v1/auth/**")
              .permitAll()
              .requestMatchers("/api/v1/demo/**").hasRole(Role.ADMIN.name())
              .requestMatchers("/api/v1/user/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.PREMIUM_USER.name())
              .anyRequest()
              .authenticated();
        })
        .authenticationProvider(authenticationProvider())
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> {
          logout.logoutUrl("/api/v1/auth/logout")
              .addLogoutHandler(logoutLogic)
              .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext());
        })
        .build();
  }
}
