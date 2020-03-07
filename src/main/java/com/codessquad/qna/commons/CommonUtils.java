package com.codessquad.qna.commons;

import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.errors.UserException;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CommonUtils {

  /**
   * Feat : Null 을 처리한 User 를 가져옵니다.
   * Desc :
   * Return : sessionedUser
   */
  public static User getSessionedUser(HttpSession session) {
    Optional<Object> optionalUser = Optional.ofNullable(session.getAttribute("sessionedUser"));
    Object user = optionalUser.orElseThrow(() -> new UserException(CustomErrorCode.USER_NOT_LOGIN));

    return (User) user;
  }

  /**
   * Feat : Null 을 처리한 Question 을 가져옵니다.
   * Desc :
   * Return : id 에 매칭되는 question
   */
  public static Question getQuestion(QuestionRepository questionRepository, Long id) {
    Optional<Question> optionalUser = questionRepository.findById(id);
    Question question = optionalUser.orElseThrow(() -> new QuestionException(CustomErrorCode.QUESTION_NOT_EXIST));

    return question;
  }

}
