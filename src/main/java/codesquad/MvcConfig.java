package codesquad;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/user/form.html").setViewName("user/form");
//        registry.addViewController("/").setViewName("index");
        registry.addViewController("/user/login.html").setViewName("user/login");
        registry.addViewController("/user/login_failed.html").setViewName("user/login_failed");
        registry.addViewController("/qna/form.html").setViewName("qna/form");
        registry.addViewController("/qna/show.html").setViewName("qna/show");
    }
}