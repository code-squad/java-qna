package io.david215.forum.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/post").setViewName("thread/post");
        registry.addViewController("/post-failed").setViewName("thread/post-failed");
        registry.addViewController("/signup").setViewName("user/signup");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/login-failed").setViewName("user/login-failed");
    }
}
