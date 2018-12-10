package codesquad;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@EnableJpaAuditing
public class QnaApplication {
    private static final Logger logger = getLogger(QnaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
        logger.debug("hello World");
    }
}
