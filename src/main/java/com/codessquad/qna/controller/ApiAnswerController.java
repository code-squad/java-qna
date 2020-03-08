package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.error.Result;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import java.rmi.NoSuchObjectException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/questions/{questionId}/answers")
public class ApiAnswerController {

  @Autowired
  AnswerRepository answerRepository;

  @Autowired
  QnaRepository qnaRepository;

  @Autowired
  UserRepository userRepository;

  @PostMapping(value = "")
  public Result create(@PathVariable("questionId") Long questionsId, String contents,
      HttpSession httpSession) {
    Question question;

    Result result = valid(httpSession);
    if (!result.isValid()) {
      return Result.fail(result.getErrorMessage());
    }

    try {
      question = qnaFindById(questionsId);
    } catch (NoSuchObjectException e) {
      return Result.fail("질문을 불러올 수가 없습니다");
    }

    User writer = HttpSessionUtils.getUserFromSession(httpSession);
    Answer answer = new Answer(question, writer, contents);
    question.addAnswer();

    qnaRepository.save(question);
    answerRepository.save(answer);

    return Result.ok(answer, question);
  }

  @PutMapping(value = "/{id}")
  public Result update(@PathVariable("id") Long answerId, String contents,
      HttpSession httpSession) {
    Answer answer;

    try {
      answer = answerFindById(answerId);
    } catch (NoSuchObjectException e) {
      return Result.fail("댓글을 불러올 수가 없습니다");
    }

    Result result = valid(httpSession, answer);
    if (!result.isValid()) {
      return Result.fail(result.getErrorMessage());
    }

    answer.update(contents);
    answerRepository.save(answer);

    return Result.ok(answer);
  }


  @DeleteMapping(value = "/{id}")
  public Result delete(@PathVariable("questionId") Long questionId,
      @PathVariable("id") Long answerId, HttpSession httpSession) {
    Answer answer;
    Question question;

    try {
      answer = answerFindById(answerId);
    } catch (NoSuchObjectException e) {
      return Result.fail("댓글을 불러올 수가 없습니다");
    }

    Result result = valid(httpSession, answer);
    if (!result.isValid()) {
      return Result.fail(result.getErrorMessage());
    }

    try {
      question = qnaFindById(questionId);
    } catch (NoSuchObjectException e) {
      return Result.fail("질문을 불러올 수 없습니다");
    }

    question.deleteAnswer();
    qnaRepository.save(question);
    answerRepository.delete(answer);

    return Result.ok(question);
  }

  private Result valid(HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return Result.fail("로그인이 필요합니다");
    }
    return Result.ok();
  }


  private Result valid(HttpSession httpSession, Answer answer) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return Result.fail("로그인이 필요합니다");
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    if (!answer.isSameWriter(sessionUser)) {
      return Result.fail("자신이 쓴 답글만 수정 혹은 삭제할 수 있습니다.");
    }
    return Result.ok();
  }

  private Answer answerFindById(Long answerId) throws NoSuchObjectException {
    return answerRepository.findById(answerId)
        .orElseThrow(() -> new NoSuchObjectException("해당 댓글을 찾지 못하였습니다."));
  }

  private Question qnaFindById(Long questionId) throws NoSuchObjectException {
    return qnaRepository.findById(questionId)
        .orElseThrow(() -> new NoSuchObjectException("해당 질문을 찾지 못하였습니다."));
  }


}
