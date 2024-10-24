package ca.on.oicr.pinery.ws.component;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.configuration.SpringDocSpecPropertiesConfiguration;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.configuration.MultipleOpenApiSupportConfiguration;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableWebMvc
@Import({
    SpringDocConfiguration.class,
    SpringDocConfigProperties.class,
    SpringDocSpecPropertiesConfiguration.class,
    SpringDocWebMvcConfiguration.class,
    MultipleOpenApiSupportConfiguration.class,
    org.springdoc.webmvc.ui.SwaggerConfig.class,
    SwaggerUiConfigProperties.class,
    SwaggerUiOAuthProperties.class,
    SpringDocUIConfiguration.class
})
public class SwaggerConfig {

  @Value("${project.name}")
  String projectName;

  @Value("${project.version}")
  String projectVersion;

  @Bean
  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
        .group("API")
        .packagesToScan("ca.on.oicr.pinery")
        .build();
  }

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(new Info().title(projectName).version(projectVersion));
  }

}