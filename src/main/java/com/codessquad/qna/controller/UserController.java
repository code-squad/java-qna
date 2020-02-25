package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
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
    return "redirect:/";
  }

  @PostMapping(value = "")
  public String create(User user) {
    userRepository.save(user);
    return "redirect:/";
  }

  @PostMapping(value = "/{id}")
  public String update(@PathVariable("id") long id, User updateUser,
      HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "redirect:/user/login";
    }
    User sessionUser = (User) HttpSessionUtils.getUserFromSession(httpSession);
    if (!sessionUser.matchId(id)) {
      throw new IllegalStateException("You can't update another User");
    }

    User user = userRepository.getOne(sessionUser.getId());
    user.update(updateUser);
    userRepository.save(user);
    return "redirect:/";
  }

  @GetMapping(value = "/logout")
  public String logout(HttpSession httpSession) {
    httpSession.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    return "redirect:/";
  }

  @GetMapping(value = "")
  public String getUsers(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/{writer}")
  public String getProfile(@PathVariable("writer") String userId, Model model) {
    model.addAttribute("user", userRepository.findByUserId(userId));
    return "user/profile";
  }

  @GetMapping(value = "/{id}/form")
  public String updateProfile(@PathVariable("id") long id, Model model,
      HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "redirect:/user/login";
    }
    User sessionUser = HttpSessionUtils.getUserFromSession(httpSession);
    if (!sessionUser.matchId(id)) {
      throw new IllegalStateException("You can't update another User");
    }
    model.addAttribute("user", userRepository.getOne(sessionUser.getId()));
    return "user/updateForm";
  }

}
