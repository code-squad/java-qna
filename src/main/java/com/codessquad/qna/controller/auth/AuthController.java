package com.codessquad.qna.controller.auth;

import com.codessquad.qna.controller.users.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.utils.PathUtil;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  @Autowired
  private UsersRepository usersRepository;

  @PostMapping("/login") //@RestController에서 하면 리다이렉트가 안된다고 한다. 왜인지는 모르겠다.
  public String login(String userId, String password, HttpSession session) {
    Users user = usersRepository.findByUserId(userId);
    if (user == null || !user.matchPassword(password)) {
      return PathUtil.USERS_LOGIN_FAILED;
    }
    session.setAttribute("sessionUser", user);
    return PathUtil.REDIRECT_TO_MAIN;
  }

  @GetMapping("/logout")
  public String usersLogout(HttpSession httpSession) {
    if (httpSession.getAttribute("sessionUser") == null) {
      throw new UnauthorizedException(PathUtil.REDIRECT_TO_USERS_LOGIN, "no one is logged in yet.");
    }
    httpSession.removeAttribute("sessionUser");
    return PathUtil.REDIRECT_TO_MAIN;
  }
}