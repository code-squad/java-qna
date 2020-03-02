package com.codessquad.qna.commons;

import com.codessquad.qna.errors.UserException;
import com.codessquad.qna.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class Utils {

  public static User getSessionedUser(HttpSession session) {
    Optional<User> optionalUser = Optional.ofNullable((User) session.getAttribute("sessionedUser"));
    User user = optionalUser.orElseThrow(() -> new UserException(CustomErrorCode.BAD_REQUEST));

    return user;
  }
}
