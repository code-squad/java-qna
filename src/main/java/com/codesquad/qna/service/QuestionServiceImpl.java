package com.codesquad.qna.service;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questionRepository.findAll());
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void update(Question question, Question updatedQuestion) {
        question.update(updatedQuestion);
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
