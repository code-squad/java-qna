package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
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

  @PostMapping(value = "/create")
  public String createUser(User user) {
    userRepository.save(user);
    return "redirect:/";
  }

  @PostMapping(value = "/{id}")
  public String modifyUser(User modifiedUser, @PathVariable("id") long id) {
    User user = userRepository.getOne(id);
    user.setName(modifiedUser.getName());
    user.setPassword(modifiedUser.getPassword());
    user.setEmail(modifiedUser.getEmail());
    userRepository.save(user);
    return "redirect:/";
  }

  @GetMapping(value = "/")
  public String getUsers(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/{writer}")
  public String getUserProfile(Model model, @PathVariable("writer") String writer) {
    model.addAttribute("user", userRepository.findUserByName(writer));
    return "user/profile";
  }

  @GetMapping(value = "/{id}/form")
  public String modifyUserProfile(Model model, @PathVariable("id") long id) {
    model.addAttribute("user", userRepository.getOne(id));
    return "user/updateForm";
  }

}
