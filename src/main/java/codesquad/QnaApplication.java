package codesquad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QnaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
    }

//    //Override MustacheAutoConfiguration to support defaultValue("")
//    @Bean
//    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader mustacheTemplateLoader,
//                                              Environment environment) {
//
//        MustacheEnvironmentCollector collector = new MustacheEnvironmentCollector();
//        collector.setEnvironment(environment);
//
//        // default value
//        return Mustache.compiler().defaultValue("")
//                .withLoader(mustacheTemplateLoader)
//                .withCollector(collector);
//    }
}