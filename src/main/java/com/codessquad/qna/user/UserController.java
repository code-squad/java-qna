package com.codessquad.qna.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/users")
public class UserController {

  private String returnRedirect = "redirect:/users";
  private String returnView = "/users";

  @Autowired
  private UserRepository userRepository;


  /**
   * 에러 발생시 사용되는 모듈.
   * 이후 공통 유틸로 추출할 예정입니다.
   */
  private void printAlert(String alertMsg, HttpServletResponse response) {
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>alert('" + alertMsg + "'); history.go(-1);</script>");
      out.flush();
    } catch (Exception e) {
      System.out.println("### printAlert Error");
      System.out.println(e);
    }
  }

  /**
   * User 생성을 위한 form 페이지로 이동합니다.
   */
  @GetMapping("/form")
  public String form(Model model) {
    return returnView + "/form";
  }

  /**
   * from 페이지에서 create 를 호출히여 User 추가합니다.
   */
  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);

    return returnRedirect + "/list";
  }

  /**
   * User 의 list 를 보여주는 list 페이지로 이동합니다.
   */
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());

    return returnView + "/list";
  }

  /**
   * list 페이지에서 선택된 id 를 상세 프로필 페이지로 보여줍니다.
   */
  @GetMapping("/{id}")
  public String profile(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).get());

    return returnView + "/profile";
  }

  /**
   * 선택된 id 의 정보를 update form 페이지로 전달해줍니다.
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).get());

    return returnView + "/update";
  }

  /**
   * 수정된 정보로 update 해줍니다.
   */
  @PutMapping("/{id}")
  public String update(@PathVariable long id, User newUser, HttpServletResponse response) {
    User origin = userRepository.findById(id).get();

    if (newUser.getOldPassword().equals(origin.getPassword())) {
      origin.update(newUser);
      userRepository.save(origin);
    } else {
      printAlert("비밀번호가 잘못되었습니다.", response);
    }

    return returnRedirect + "/list";
  }
}
