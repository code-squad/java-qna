package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
public class PageUtils {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/test")
    public List<Question> create() {
        List<Question> questions = questionRepository.findAll();
        return questions;
    }
}
