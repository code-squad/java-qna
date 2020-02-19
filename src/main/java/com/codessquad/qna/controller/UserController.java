package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private static Logger log = LoggerFactory.getLogger(QnaController.class);
  private static List<User> users = new ArrayList<>();

  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/user/create")
  public String createUser(@ModelAttribute User user) {
    userRepository.save(user);
    return "redirect:/users";
  }

  @GetMapping(value = "/users")
  public String getUsers(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/users/{id}")
  public String getProfile(Model model, @PathVariable("id") String id) {
    model.addAttribute("user", userRepository.findById(convertToString(id)));
    return "user/profile";
  }

  private Long convertToString(String id) {
    return Long.parseLong(id);
  }
}
