package com.codessquad.qna;

import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.dto.PostsResponseDto;
import com.codessquad.qna.web.dto.UsersResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;
  private final UsersService usersService;
  private final UsersRepository usersRepository;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }

  @GetMapping("/posts-list")
  public String anotherIndex(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }

  //"/users/logout" 로그아웃 기능 만들어야 함
  //"/users/update" 개인정보 수정 기능 만들어야 함
  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/users/login")
  public String usersLogin() {
    return "users-login";
  }

  @GetMapping("/users/login/fail")
  public String usersLoginFailed() {
    return "users-login-failed";
  }

  @GetMapping("/users/register")
  public String usersRegister() {
    return "users-register";
  }

  @GetMapping("/logout")
  public String usersLogout(HttpSession httpSession) {
    httpSession.removeAttribute("user");
    System.out.println("logout succeed");
    return "redirect:/";
  }

  @GetMapping("/users/show")
  public String usersShow(Model model) {
    model.addAttribute("users", usersService.findAllDesc());
    return "users-show";
  }

  @GetMapping("/users/update/{Id}")
  public String usersUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (HttpSessionUtils.isLoggedIn(httpSession)) {
      return "redirect:/users/login";
    }
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    if (!sessionUser.getId().equals(Id)) {
      throw new IllegalStateException("invalid user info");
    }
    UsersResponseDto responseDto = usersService.findById(Id);
    model.addAttribute("user", responseDto);
    return "users-update";
  }

  @GetMapping("/posts/update/{Id}")
  public String postsUpdate(@PathVariable Long Id, Model model) {
    PostsResponseDto responseDto = postsService.findById(Id);
    model.addAttribute("posts", responseDto);
    return "posts-update";
  }

  @PostMapping("/login") //@RestController에서 하면 리다이렉트가 안된다고 한다. 왜인지는 모르겠다.
  public String login(String userId, String password, HttpSession session) {
    Users user = usersRepository.findByUserId(userId);
    if (user == null) {
      System.out.println("login failure!");
      return "users-login-failed";
    }
    if (!password.equals(user.getPassword())) {
      System.out.println("login failure!");
      return "users-login-failed";
    }
    session.setAttribute("sessionUser", user);
    System.out.println("login success");
    return "redirect:/";
  }
}