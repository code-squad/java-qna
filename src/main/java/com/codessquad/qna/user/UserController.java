package com.codessquad.qna.user;

import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.errors.UserException;
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
   * Feat : User 생성을 위한 hbs 로 이동합니다.
   * Desc :
   * Return : /users/form
   */
  @GetMapping("/form")
  public String form(Model model) {
    return "/users/form";
  }

  /**
   * Feat : User 를 생성합니다.
   * Desc :
   * Return : redirect:/users/list
   */
  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);

    return "redirect:/users/list";
  }

  /**
   * Feat : User list 를 가져옵니다.
   * Desc :
   * Return : /users/list
   */
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());

    return "/users/list";
  }

  /**
   * Feat : id 를 키 값으로 User 를 가져옵니다.
   * Desc : user가 존재하지 않으면 customErrorCode 에 따라 처리합니다.
   * Return : id 에 매칭된 User.
   */
  private User getUser(Long id, CustomErrorCode customErrorCode) {
    Optional<User> optionalUser = userRepository.findById(id);
    User user = optionalUser.orElseThrow(() -> new UserException(customErrorCode));
    return user;
  }

  /**
   * Feat : userId 를 키 값으로 User 를 가져옵니다.
   * Desc : user가 존재하지 않으면 customErrorCode 에 따라 처리합니다.
   * Return : id 에 매칭된 User.
   */
  private User getUser(String userId, CustomErrorCode customErrorCode) {
    Optional<User> optionalUser = userRepository.findByUserId(userId);
    User user = optionalUser.orElseThrow(() -> new UserException(customErrorCode));
    return user;
  }

  /**
   * Feat : User 의 상세 정보를 가져옵니다.
   * Desc : user가 존재하지 않으면 customErrorCode 에 따라 처리합니다.
   * Return : /users/profile
   */
  @GetMapping("/{id}")
  public String profile(@PathVariable Long id, Model model) {
    User user = getUser(id, CustomErrorCode.BAD_REQUEST);
    model.addAttribute("user", user);

    return "/users/profile";
  }

  /**
   * Feat : update 를 위한 User 상세 정보를 가져옵니다.
   * Desc : user가 존재하지 않으면 customErrorCode 에 따라 처리합니다.
   * Return : /users/update
   */
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable Long id, Model model) {
    User user = getUser(id, CustomErrorCode.BAD_REQUEST);
    model.addAttribute("user", user);

    return "/users/update";
  }

  /**
   * Feat : User 정보를 update 해줍니다.
   * Desc : password 가 같지 않은 경우 error 처리합니다.
   * Return : redirect:/users/list
   */
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, User newUser, Model model) {
    log.info("### update()");
    User originUser = getUser(id, CustomErrorCode.USER_NOT_EXIST);

    if (originUser.validatePassword(newUser.getOldPassword())) {
      originUser.update(newUser);
      userRepository.save(originUser);

      return "redirect:/users/list";
    }

    model.addAttribute("wrongPassword", true);

    return "/users/update";
  }

  /**
   * Feat : login.hbs 로 이동합니다.
   * Desc :
   * Return : /users/login
   */

  @GetMapping("/loginForm")
  public String loginForm(Model model) {
    log.info("### loginForm()");
    return "/users/login";
  }

  /**
   * Feat : login 이 성공하는 경우 sessionedUser 를 등록해줍니다.
   * Desc : password 가 틀린 경우 다시 login hbs 로 이동합니다.
   * Return : redirect:/
   */
  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session, Model model) {
    log.info("### login()");

    User user = getUser(userId, CustomErrorCode.USER_NOT_EXIST);

    if (user.validatePassword(password)) {
      session.setAttribute("sessionedUser", user);
      return "redirect:/";
    }

    throw new UserException(CustomErrorCode.USER_NOT_MATCHED_PASSWORD);
  }

  /**
   * Feat : logout 이 성공하는 경우 sessionedUser 를 지워줍니다.
   * Desc :
   * Return : redirect:/
   */
  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("sessionedUser");
    return "redirect:/";
  }
}
