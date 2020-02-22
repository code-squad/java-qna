package com.codessquad.qna.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

public class WebInitializer implements WebApplicationInitializer {
    // HTTP PUT, DELETE 메소드를 사용하기 위한 설정
    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.addFilter("httpMethodFilter", HiddenHttpMethodFilter.class)
                      .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}
