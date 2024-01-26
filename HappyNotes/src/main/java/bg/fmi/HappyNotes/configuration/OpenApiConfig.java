package bg.fmi.HappyNotes.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${happy-notes.open-api.dev-url}")
  private String devUrl;

  @Bean
  public OpenAPI happyNotesOpenApi() {
    // Server configuration
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in dev environment");

    // Contact configuration
    Contact contact = new Contact()
        .email("viktoiyageorge@gmail.com")
        .name("Viktoriya Georgieva");

    // API information
    Info apiInfo = new Info()
        .title("Happy Notes API")
        .description("API for Happy Notes application")
        .version("1.0.0")
        .contact(contact);

    // Security scheme configuration
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .name("BearerAuth")
        .scheme("bearer")
        .in(SecurityScheme.In.HEADER);

    // Security requirement
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

    // OpenAPI configuration
    return new OpenAPI()
        .info(apiInfo)
        .addServersItem(devServer)
        .addSecurityItem(securityRequirement)
        .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("BearerAuth", securityScheme));
  }
}
