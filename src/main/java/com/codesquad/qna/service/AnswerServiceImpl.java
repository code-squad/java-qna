package com.codesquad.qna.service;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer findByQuestionIdAndId(Long questionId, Long id) {
        return answerRepository.findByQuestionIdAndId(questionId, id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void update(Answer updatingAnswer, Answer updatedAnswer) {
        updatingAnswer.update(updatedAnswer);
        answerRepository.save(updatingAnswer);
    }

    @Override
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
