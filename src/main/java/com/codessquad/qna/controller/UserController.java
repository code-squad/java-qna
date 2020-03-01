package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.error.Result;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  private static Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/login")
  public String login(String userId, String password, HttpSession httpSession) {
    User user = userRepository.findByUserId(userId);
    if (user == null) {
      return "user/login";
    }
    if (!user.matchPassword(password)) {
      return "user/login_failed";
    }
    httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
    return "redirect:/questions/";
  }

  @GetMapping(value = "/logout")
  public String logout(HttpSession httpSession) {
    httpSession.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    httpSession.invalidate();
    return "redirect:/questions/";
  }

  @PostMapping(value = "")
  public String create(User user) {
    userRepository.save(user);
    return "redirect:/questions/";
  }

  @PutMapping(value = "/{id}")
  public String update(@PathVariable("id") long id, User updateUser,
      Model model, HttpSession httpSession) {
    User user = userRepository.getOne(id);
    Result result = valid(httpSession, user);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }

    user.update(updateUser);
    userRepository.save(user);
    return "redirect:/users/";
  }

  @GetMapping(value = "")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/{writer}")
  public String profile(@PathVariable("writer") String userId, Model model) {
    model.addAttribute("user", userRepository.findByUserId(userId));
    return "user/profile";
  }

  @GetMapping(value = "/{id}/form")
  public String update(@PathVariable("id") long id, Model model,
      HttpSession httpSession) {
    User user = userRepository.getOne(id);
    Result result = valid(httpSession, user);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }

    model.addAttribute("user", user);
    return "user/update_form";
  }

  private Result valid(HttpSession httpSession, User user) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return Result.fail("로그인이 필요합니다");
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    if (!user.isSameUser(sessionUser)) {
      return Result.fail("자기 자신만 수정 혹은 삭제할 수 있습니다");
    }
    return Result.ok();
  }


}
