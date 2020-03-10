package com.codessquad.qna;

import com.codessquad.qna.controller.users.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.service.answers.AnswersService;
import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.PathUtils;
import com.codessquad.qna.web.dto.answers.AnswersResponseDto;
import com.codessquad.qna.web.dto.posts.PostsResponseDto;
import com.codessquad.qna.web.dto.users.UsersResponseDto;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

  private final PostsService postsService;
  private final UsersService usersService;
  private final AnswersService answersService;
  private final UsersRepository usersRepository;

  public IndexController(PostsService postsService, UsersService usersService, AnswersService answersService,
      UsersRepository usersRepository) {
    this.postsService = postsService;
    this.usersService = usersService;
    this.answersService = answersService;
    this.usersRepository = usersRepository;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    return PathUtils.INDEX;
  }

  @GetMapping("/posts-list")
  public String anotherIndex(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    return PathUtils.INDEX;
  }

  @GetMapping("/posts/show/{Id}")
  public String postsShow(@PathVariable Long Id, Model model) {
    PostsResponseDto responseDto = postsService.findById(Id);
    model.addAttribute("posts", responseDto);
    //model.addAttribute("answers", answersService.findAllDesc());
    return PathUtils.POSTS_SHOW;
  }

  @GetMapping("/posts/save")
  public String postsSave(Model model, HttpSession httpSession) {
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    model.addAttribute("author", sessionUser);
    return PathUtils.POSTS_SAVE;
  }

  @GetMapping("/users/login")
  public String usersLogin() {
    return PathUtils.USERS_LOGIN;
  }

  @GetMapping("/users/login/fail")
  public String usersLoginFailed() {
    return PathUtils.USERS_LOGIN_FAILED;
  }

  @GetMapping("/users/register")
  public String usersRegister() {
    return PathUtils.USERS_REGISTER;
  }

  @GetMapping("/logout")
  public String usersLogout(HttpSession httpSession) {
    httpSession.removeAttribute("sessionUser");
    System.out.println("logout succeed");
    return PathUtils.REDIRECT_TO_MAIN;
  }

  @GetMapping("/users/show")
  public String usersShow(Model model) {
    model.addAttribute("users", usersService.findAllDesc());
    return PathUtils.USERS_SHOW;
  }

  @GetMapping("/users/update/{Id}")
  public String usersUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (HttpSessionUtils.isLoggedIn(httpSession)) {
      return PathUtils.REDIRECT_TO_USERS_LOGIN;
    }
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    if (!sessionUser.getId().equals(Id)) {
      return PathUtils.INVALID_ACCESS;
    }
    UsersResponseDto responseDto = usersService.findById(Id);
    model.addAttribute("user", responseDto);
    return PathUtils.USERS_UPDATE;
  }

  @GetMapping("/answers/update/{Id}")
  public String answersUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (HttpSessionUtils.isLoggedIn(httpSession)) {
      return PathUtils.REDIRECT_TO_USERS_LOGIN;
    }
    AnswersResponseDto responseDto = answersService.findById(Id);
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    if (!sessionUser.matchId(Id)) { //조금 더 객체지향스럽게 수정
      return PathUtils.INVALID_ACCESS;
    }
    model.addAttribute("answers", responseDto);
    return PathUtils.ANSWERS_UPDATE;
  }

  @GetMapping("/posts/update/{Id}")
  public String postsUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (HttpSessionUtils.isLoggedIn(httpSession)) {
      return PathUtils.REDIRECT_TO_USERS_LOGIN;
    }
    PostsResponseDto responseDto = postsService.findById(Id);
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    if (!sessionUser.matchId(Id)) {
      return PathUtils.INVALID_ACCESS;
    }
    model.addAttribute("posts", responseDto);
    model.addAttribute("author", sessionUser);
    return PathUtils.POSTS_UPDATE;
  }

  @PostMapping("/login") //@RestController에서 하면 리다이렉트가 안된다고 한다. 왜인지는 모르겠다.
  public String login(String userId, String password, HttpSession session) {
    Users user = usersRepository.findByUserId(userId);
    if (user == null) {
      return PathUtils.USERS_LOGIN_FAILED;
    }
    if (!user.matchPassword(password)) {
      return PathUtils.USERS_LOGIN_FAILED;
    }
    session.setAttribute("sessionUser", user);
    return PathUtils.REDIRECT_TO_MAIN;
  }
}