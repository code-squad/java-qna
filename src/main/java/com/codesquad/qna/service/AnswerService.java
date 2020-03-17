package com.codesquad.qna.service;

import com.codesquad.qna.domain.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAllByQuestionId(Long questionId);

    Answer save(Answer answer);

    Answer findByQuestionIdAndId(Long questionId, Long id);

    void update(Answer updatingAnswer, Answer updatedAnswer);

    void delete(Answer answer);
}
