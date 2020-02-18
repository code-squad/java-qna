package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

  private static List<User> userList = new ArrayList<>();

  @GetMapping("/index")
  public String goIndex(Model model) {
    return "index";
  }

  @GetMapping("/user/form")
  public String goForm(Model model) {
    return "user/form";
  }

  @PostMapping("/users")
  public String createUser(User user, Model model) {
    userList.add(user);
    return "redirect:/list";
  }

  @RequestMapping("/users/{userId}")
  public String page(@PathVariable String userId, Model model) {
    for (User user : userList) {
      if (userId.equals(user.getUserId()))
        model.addAttribute("user", user);
    }

    return "user/profile";
  }

  @GetMapping("user/list")
  public String goList(Model model) {
    model.addAttribute("users", userList);
    return "user/list";
  }
}
