package com.codessquad.qna.commons;

import com.codessquad.qna.errors.UserException;
import com.codessquad.qna.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class Utils {

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
}
