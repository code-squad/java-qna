package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
  private List<User> userList = new ArrayList<>();

  @PostMapping("/create")
  public String createUser(User user) {
    userList.add(user);
    return "redirect:/user/list";
  }

  @GetMapping("/list")
  public String getUserList(Model model) {
    model.addAttribute("users", userList);
    return "user/list";
  }

  @GetMapping("/{userId}")
  public String showUserProfile(@PathVariable String userId, Model model) throws NullPointerException {
    for (User user : userList) {
      if (userId.equals(user.getUserId())) {
        model.addAttribute("user", user);
        break;
      }
      throw new NullPointerException("no user");
    }
    return "user/profile";
  }
}
