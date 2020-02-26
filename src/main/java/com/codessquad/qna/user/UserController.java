package com.codessquad.qna.user;

import com.codessquad.qna.errors.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  /**
   * User 생성을 위한 form 페이지로 이동합니다.
   */
  @GetMapping("/form")
  public String form(Model model) {
    return "/users/form";
  }

  /**
   * from 페이지에서 create 를 호출히여 User 추가합니다.
   */
  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);

    return "redirect:/users/list";
  }

  /**
   * User 의 list 를 보여주는 list 페이지로 이동합니다.
   */
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());

    return "/users/list";
  }

  /**
   * list 페이지에서 선택된 id 를 상세 프로필 페이지로 보여줍니다.
   */
  @GetMapping("/{id}")
  public String profile(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).orElseThrow(ForbiddenException::new));

    return "/users/profile";
  }

  /**
   * 선택된 id 의 정보를 update form 페이지로 전달해줍니다.
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).orElseThrow(ForbiddenException::new));

    return "/users/update";
  }

  /**
   * 수정된 정보로 update 해줍니다.
   */
  @PutMapping("/{id}")
  public String update(@PathVariable long id, User newUser) {
    User origin = userRepository.findById(id).orElseThrow(ForbiddenException::new);

    if (!(newUser.getOldPassword().equals(origin.getPassword()))) {
      throw new ForbiddenException();
    }

    origin.update(newUser);
    userRepository.save(origin);

    return "redirect:/users/list";
  }
}
