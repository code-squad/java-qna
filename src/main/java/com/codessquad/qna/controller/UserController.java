package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
  private List<User> userList = new ArrayList<>();

  @PostMapping("/user/create")
  public String createUser(User user) {
    userList.add(user);
    return "redirect:/user/list";
  }

  @GetMapping("/user/list")
  public String getUserList(Model model) {
    model.addAttribute("users", userList);
    return "list";
  }
}
