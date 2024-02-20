package bg.fmi.HappyNotes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://happy-notes-5covcfpgp-viktoriyageorges-projects.vercel.app")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE")
                .allowedHeaders("Content-Type")
                .allowCredentials(true);
    }
}
