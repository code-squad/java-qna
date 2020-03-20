package com.codessquad.qna.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
//  @Autowired
//  @Qualifier(value = "userInterceptor")
//  private HandlerInterceptor interceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserLoginCheckInterceptor())
        .addPathPatterns("/users/update/**")
        .addPathPatterns("/answers/update/**")
        .addPathPatterns("/posts/update/**");
  }
}
