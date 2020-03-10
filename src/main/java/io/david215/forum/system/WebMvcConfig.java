package io.david215.forum.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/new-thread").setViewName("thread/form");
        registry.addViewController("/post-failed").setViewName("thread/post-failed");
        registry.addViewController("/signup").setViewName("user/form");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/login-failed").setViewName("user/login-failed");
    }
}
