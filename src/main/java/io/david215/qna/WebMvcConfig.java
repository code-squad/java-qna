package io.david215.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/new-question").setViewName("qna/form");
        registry.addViewController("/signup").setViewName("users/form");
        registry.addViewController("/login").setViewName("users/login");
        registry.addViewController("/login-failed").setViewName("users/login-failed");
    }
}
