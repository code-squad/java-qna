package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
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
    User user = userRepository.findUserByUserId(userId);
    if (user == null) {
      return "user/login";
    }
    if (!password.equals(user.getPassword())) {
      return "user/login_failed";
    }
    httpSession.setAttribute("sessionUser", user);
    return "redirect:/";
  }

  @PostMapping(value = "/create")
  public String createUser(User user) {
    userRepository.save(user);
    return "redirect:/";
  }

  @PostMapping(value = "/{id}")
  public String updateUser(@PathVariable("id") long id, User updateUser,
      HttpSession httpSession) {

    Object sessionObject = httpSession.getAttribute("sessionUser");
    if (sessionObject == null) {
      return "redirect:/user/login";
    }
    User sessionUser = (User) sessionObject;
    if (!sessionUser.getUserId().equals(id)) {
      throw new IllegalStateException("You can't update another User");
    }

    User user = userRepository.getOne(sessionUser.getId());
    user.setName(updateUser.getName());
    user.setPassword(updateUser.getPassword());
    user.setEmail(updateUser.getEmail());
    userRepository.save(user);
    return "redirect:/";
  }

  @GetMapping(value = "/logout")
  public String logout(HttpSession httpSession) {
    httpSession.removeAttribute("sessionUser");
    return "redirect:/";
  }

  @GetMapping(value = "/list")
  public String getUsers(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/{writer}")
  public String getUserProfile(@PathVariable("writer") String writer, Model model) {
    model.addAttribute("user", userRepository.findUserByWriter(writer));
    return "user/profile";
  }

  @GetMapping(value = "/{id}/form")
  public String modifyUserProfile(@PathVariable("id") long id, Model model,
      HttpSession httpSession) {
    Object sessionObject = httpSession.getAttribute("sessionUser");
    if (sessionObject == null) {
      return "redirect:/user/login";
    }
    User sessionUser = (User) sessionObject;
    if (!sessionUser.getUserId().equals(id)) {
      throw new IllegalStateException("You can't update another User");
    }
    model.addAttribute("user", userRepository.getOne(sessionUser.getId()));
    return "user/updateForm";
  }

}
