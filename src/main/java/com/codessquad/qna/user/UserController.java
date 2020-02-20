package com.codessquad.qna.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/users")
public class UserController {

  private String returnRedirectUrl = "redirect:/users";
  private String returnForwardUrl = "/users";

  @Autowired
  private UserRepository userRepository;

  private void printAlert(String alertMsg, HttpServletResponse response) {
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>alert('비밀번호가 잘못되었습니다.'); history.go(-1);</script>");
      out.flush();
    } catch (Exception e) {
      System.out.println("### printAlert Error");
      System.out.println(e);
    }
  }

  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);
    return returnRedirectUrl + "/list";
  }

  @GetMapping("/form")
  public String form(Model model) {
    return returnForwardUrl + "/form";
  }

  @GetMapping("/{userId}/form")
  public ModelAndView updateForm(@PathVariable long userId) {
    ModelAndView mav = new ModelAndView("/users/updateForm");
    mav.addObject("user", userRepository.findById(userId).get());

    return mav;
  }

  @PutMapping("/{userId}")
  public String update(@PathVariable long userId, User newUser, HttpServletResponse response) {
    User origin = userRepository.findById(userId).get();

    if (newUser.getOldPassword().equals(origin.getPassword())) {
      origin.update(newUser);
      userRepository.save(origin);
    } else {
      printAlert("비밀번호가 잘못되었습니다.", response);
    }

    return returnRedirectUrl + "/list";
  }

  @GetMapping("/{userId}")
  public ModelAndView profile(@PathVariable long userId) {
    ModelAndView mav = new ModelAndView("/users/profile");
    mav.addObject("user", userRepository.findById(userId).get());

    return mav;
  }

  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());

    return returnForwardUrl + "/list";
  }
}
