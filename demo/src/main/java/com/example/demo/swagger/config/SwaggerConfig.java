package com.example.demo.swagger.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Value("${swagger.api.version}")
    private String SWAGGER_API_VERSION;

    @Value("${swagger.license.text}")
    private String LICENSE_TEXT;

    @Value("${swagger.title}")
    private String TITLE;

    @Value("${swagger.description}")
    private String DESCRIPTION;

    @Value("${swagger.host}")
    private String HOST;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .license(LICENSE_TEXT)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Bean
    public Docket priductApi() {
        final Set<String> produces = new HashSet<>();
        produces.add(MediaType.APPLICATION_JSON_VALUE);

        final Set<String> protocols = new HashSet<>();
//        protocols.add("https");
        protocols.add("http");

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .produces(produces)
                .consumes(produces)
                .protocols(protocols)
                .apiInfo(apiInfo())
                .host(HOST)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

