package com.codessquad.qna;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class QnaApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(QnaApplication.class, args);
		Handlebars handlebars = new Handlebars();
		Template template = handlebars.compileInline("Hello {{this}}!");
		System.out.println(template.apply("Handlebars.java"));
	}
}
