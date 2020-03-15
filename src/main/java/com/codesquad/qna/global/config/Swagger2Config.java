package com.codesquad.qna.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({"local","dev"})
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**")) // 필터링할 URL
                .build()
                .apiInfo(apiInfo())
                ;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Jay Simple QnA")
                .description("간단한 질문 답변 게시판입니다.")
                .version("1.0")
                .build();
    }
}

