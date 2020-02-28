package com.codessquad.qna;

import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.service.users.UsersService;
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

  @GetMapping("/")
  public String index(Model model){
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }

  @GetMapping("/posts-list")
  public String anotherIndex(Model model){
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }

  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/users/login")
  public String usersLogin() { return "users-login"; }

  @GetMapping("/users/login/fail")
  public String usersLoginFailed() { return "users-login-failed"; }

  @GetMapping("/users/register")
  public String usersRegister() { return "users-register"; }

  @GetMapping("/users/show")
  public String usersShow(Model model) {
    model.addAttribute("users", usersService.findAllDesc());
    return "users-show";
  }

  @GetMapping("/users/update/{Id}")
  public String usersUpdate(@PathVariable Long Id, Model model) {
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
}