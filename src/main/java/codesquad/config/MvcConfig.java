package codesquad.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(MvcConfig.class);

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        log.info("MvcConfig를 거쳐감");

        registry.addViewController("/users/form").setViewName("/user/form");
        registry.addViewController("/users/loginForm").setViewName("/user/login");
    }
}