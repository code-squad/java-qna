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


  @RequestMapping(value = "/login")
  public String loginUser(String userId, String password, HttpSession httpSession) {
    User user = userRepository.findUserByUserId(userId);
    if (user == null) {
      return "user/login";
    }
    if (!password.equals(user.getPassword())) {
      return "user/login_failed";
    }
    httpSession.setAttribute("user", user);
    return "redirect:/";
  }

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

  @GetMapping(value = "/list")
  public String getUsers(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  @GetMapping(value = "/{name}")
  public String getUserProfile(Model model, @PathVariable("name") String name) {
    model.addAttribute("user", userRepository.findUserByName(name));
    return "user/profile";
  }

  @GetMapping(value = "/{id}/form")
  public String modifyUserProfile(Model model, @PathVariable("id") long id) {
    model.addAttribute("user", userRepository.getOne(id));
    return "user/updateForm";
  }

}
