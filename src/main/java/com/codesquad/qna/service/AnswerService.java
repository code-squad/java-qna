package com.codesquad.qna.service;

import com.codesquad.qna.domain.Answer;

public interface AnswerService {
    Answer save(Answer answer);

    Answer findByQuestionIdAndId(Long questionId, Long id);

    void update(Answer updatingAnswer, Answer updatedAnswer);

    void delete(Answer answer);
}
