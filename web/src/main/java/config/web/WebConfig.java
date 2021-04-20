package config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import config.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Set;
import java.util.TimeZone;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan({"exceptions", "converters", "controllers"})
public class WebConfig {
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = ImmutableSet.of(
            "application/json"
    );

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        return objectMapper;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("controllers"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo())
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Service Station REST Api")
                .contact(new Contact("Misha Avizhen", null, "misha33379@mail.ru"))
                .description("Service Station")
                .build();
    }
}
