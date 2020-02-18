package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private static List<User> users = new ArrayList<>();
  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @PostMapping(value = "/user/create")
  public String createUser(@ModelAttribute User user) {
    users.add(user);
    return "redirect:/users";
  }

  @GetMapping(value = "/users")
  public String getAllUsers(Model model) {
    model.addAttribute("users", users);
    return "user/list";
  }

  @GetMapping(value = "/users/{userId}")
  public String getUserProfile(Model model, @PathVariable("userId") String userId) {
    User user = users.stream()
        .filter(x -> userId.equals(x.getUserId()))
        .findAny()
        .orElse(null);
    model.addAttribute("user", user);
    return "user/profile";
  }

}
