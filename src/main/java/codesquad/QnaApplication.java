package codesquad;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class QnaApplication {
    private static final Logger log = getLogger(QnaApplication.class);

    public static void main(String[] args) {
        log.info("aaaaaaa");
        SpringApplication.run(QnaApplication.class, args);
    }
}
