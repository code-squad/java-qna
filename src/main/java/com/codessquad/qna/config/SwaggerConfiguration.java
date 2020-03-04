package com.codessquad.qna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket swaggerApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(swaggerInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.codessquad.qna.controller"))
        .paths(PathSelectors.ant("/api/**"))
        .build();
//        .useDefaultResponseMessages(false);
  }

  private ApiInfo swaggerInfo() {
    return new ApiInfoBuilder().title("Spring API Documentation")
        .title("Java qna API")
        .description("코드스쿼드 Java-qna API 문서")
        .license("Han")
        .version("1")
        .build();
  }
}
