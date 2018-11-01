package ca.on.oicr.pinery.ws.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.ImmutableList;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  
  @Value("${project.name}")
  String projectName;
  
  @Value("${project.version}")
  String projectVersion;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(metaData())
        .globalResponseMessage(RequestMethod.GET, ImmutableList.of(new ResponseMessageBuilder()
            .code(200)
            .message("OK")
            .build()));
  }
  
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title(projectName)
        .version(projectVersion)
        .build();
  }

}
