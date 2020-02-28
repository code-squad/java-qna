package io.david215.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/qna/form").setViewName("qna/form");
        registry.addViewController("/qna/show").setViewName("qna/show");
        registry.addViewController("/users/form").setViewName("users/form");
        registry.addViewController("/users/login").setViewName("users/login");
        registry.addViewController("/users/login-failed").setViewName("users/login-failed");
    }
}
