package com.codesquad.qna.service;

import com.codesquad.qna.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    Question save(Question question);

    void update(Question updatingQuestion, Question updatedQuestion);

    void delete(Question question);

    Question findById(Long id);
}
