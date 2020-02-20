package com.codessquad.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; //deprecated

//정적 페이지들은 컨트롤러를 별도로 생성하지 않고 뷰 화면으로 바로 매핑을 한다.
@Configuration
public class MvcConfig implements WebMvcConfigurer {
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    registry.addViewController("/login").setViewName("../static/user/login");
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/user/form").setViewName("user/form");
  }
}