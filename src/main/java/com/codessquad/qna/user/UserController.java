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
@RequestMapping("/users")
public class UserController {

  private static List<User> userList = new ArrayList<>();
  private String returnRedirectUrl = "redirect:/users";
  private String returnForwardUrl = "/user";

  @PostMapping("")
  public String createUser(User user) {
    userList.add(user);

    return returnRedirectUrl + "/list";
  }

  @GetMapping("/form")
  public String goForm(Model model) {
    return returnForwardUrl + "/form";
  }

  @GetMapping("/{userId}")
  public String goProfile(@PathVariable String userId, Model model) {
    for (User user : userList) {
      if (userId.equals(user.getUserId()))
        model.addAttribute("user", user);
    }

    return returnForwardUrl + "/profile";
  }

  @GetMapping("/list")
  public String goList(Model model) {
    model.addAttribute("users", userList);

    return returnForwardUrl + "/list";
  }
}
