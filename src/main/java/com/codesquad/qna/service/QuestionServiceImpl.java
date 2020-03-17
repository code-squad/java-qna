package com.codesquad.qna.service;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public Question save(Question question) {
        return null;
    }

    @Override
    public void update(Question updatingQuestion, Question updatedQuestion) {

    }

    @Override
    public void delete(Question question) {

    }

    @Override
    public Question findById(Long id) {
        return null;
    }
}
