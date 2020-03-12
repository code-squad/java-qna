package com.codessquad.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EntityScan
@EnableJpaAuditing
@SpringBootApplication
public class QnaApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(QnaApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
