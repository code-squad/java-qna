package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private static ArrayList<User> users = new ArrayList<>();

  @PostMapping(value = "/user/create")
  public String createUser(@ModelAttribute User user) {
    users.add(user);
    return "redirect:/users";
  }

  @GetMapping(value = "/users")
  public String showAllUsers(Model model) {
    model.addAttribute("users", users);
    return "user/list";
  }

}
