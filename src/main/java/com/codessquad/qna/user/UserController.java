package com.codessquad.qna.user;

import com.codessquad.qna.errors.ForbiddenException;
import com.codessquad.qna.errors.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  /**
   * User 생성을 위한 form.html 로 이동합니다.
   */
  @GetMapping("/form")
  public String form(Model model) {
    return "/users/form";
  }

  /**
   * from.html 에서 호출하며 User 를 추가합니다.
   */
  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);

    return "redirect:/users/list";
  }

  /**
   * User 의 list 를 보여주는 list.html 로 이동합니다.
   */
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());

    return "/users/list";
  }

  /**
   * list.html 에서 선택된 id 의 정보를 profile.html 에서 출력합니다.
   */
  @GetMapping("/{id}")
  public String profile(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).orElseThrow(ForbiddenException::new));

    return "/users/profile";
  }

  /**
   * 선택된 id 의 정보를 update.html 로 전달해줍니다.
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable long id, Model model) {
    model.addAttribute("user", userRepository.findById(id).orElseThrow(ForbiddenException::new));

    return "/users/update";
  }

  /**
   * 수정된 정보를 update 해줍니다.
   */
  @PutMapping("/{id}")
  public String update(@PathVariable long id, User newUser) {
    User origin = userRepository.findById(id).orElseThrow(ForbiddenException::new);

    if (!(origin.getPassword().equals(newUser.getOldPassword()))) {
      throw new ForbiddenException();
    }

    origin.update(newUser);
    userRepository.save(origin);

    return "redirect:/users/list";
  }

  /**
   * Login.html 로 이동합니다.
   */
  @GetMapping("/loginForm")
  public String loginForm(Model model) {
    log.info("loginForm()");
    return "/users/login";
  }

  /**
   * 올바른 User 인지 확인합니다.
   */
  public boolean isMatched(User findUser, String password) {
    return (findUser.getId() != 0
        && findUser.getPassword().equals(password))
        ? true : false;
  }


  /**
   * login 성공 : 시작 페이지로 이동합니다.
   * login 실패 : 입력된 정보를 가지고 login.html 로 이동합니다.
   */
  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session, Model model) {
    log.info("login()");

    User user = Optional.ofNullable(userRepository.findByUserId(userId)).orElse(new User());

    if (isMatched(user, password)) {
      session.setAttribute("sessionedUser", user);
      return "redirect:/";
    }

    model.addAttribute("userId", userId);
    model.addAttribute("wrongPassword", true);

    return "/users/login";
  }
}
