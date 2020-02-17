package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

  private static List<User> userList = new ArrayList<>();

  @GetMapping("/form")
  public String goForm() {
    return "user/form";
  }

  @PostMapping("/users")
  public String createUser(User user, Model model) {
    userList.add(user);
//    for(User userInList : userList){
//      model.addAttribute("testName", "testValue");
//    }

    model.addAttribute("user", user);

    return "user/list";
  }
}
