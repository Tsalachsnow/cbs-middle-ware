package com.cbs.middleware.config;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefinitions = {
                        @ApiKeyAuthDefinition(key = "Api-Key", name = "Api-Key", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
                }
        )
)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cbs.middleware.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo());
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**")) // Adjust this to match your authorization paths
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Api-Key", authorizationScopes));
    }

    private springfox.documentation.service.SecurityScheme securityScheme() {
        return new springfox.documentation.service.ApiKey("Api-Key", "Api-Key", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CBS Middleware API Documentation")
                .description("Documentation for CBS Middleware API endpoints")
                .version("1.0")
                .build();
    }
}
