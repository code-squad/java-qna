package com.codessquad.qna.web.services;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) throws NotFoundException {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void register(Question newQuestion) {
        questionRepository.save(newQuestion);
    }

    public void edit(Question targetQuestion, Question newQuestion) {
        questionRepository.save(targetQuestion.merge(newQuestion));
    }

    public void delete(Question targetQuestion) {
        questionRepository.delete(targetQuestion);
    }
}
