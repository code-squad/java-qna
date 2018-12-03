package codesquad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QnaApplication {
    private static final Logger logger = LoggerFactory.getLogger(QnaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
        logger.debug("asdfsdfdsnnnnnnnnnnnnnnnnnnnnnnnn");
    }
}
