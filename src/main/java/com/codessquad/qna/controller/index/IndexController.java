package com.codessquad.qna.controller.index;

import com.codessquad.qna.domain.Users;
import com.codessquad.qna.service.answers.AnswersService;
import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.utils.HttpSessionUtil;
import com.codessquad.qna.utils.PathUtil;
import com.codessquad.qna.web.dto.answers.AnswersResponseDto;
import com.codessquad.qna.web.dto.posts.PostsResponseDto;
import com.codessquad.qna.web.dto.users.UsersResponseDto;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class IndexController {

  private final PostsService postsService;
  private final UsersService usersService;
  private final AnswersService answersService;
  //private final ResultsCheckUtil resultsCheckUtil = new ResultsCheckUtil();

  public IndexController(PostsService postsService, UsersService usersService,
      AnswersService answersService) {
    this.postsService = postsService;
    this.usersService = usersService;
    this.answersService = answersService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());
    return PathUtil.INDEX;
  }

  @GetMapping("/posts/show/{Id}")
  public String postsShow(@PathVariable Long Id, Model model) {
    PostsResponseDto responseDto = postsService.findById(Id);
    model.addAttribute("posts", responseDto);
    return PathUtil.POSTS_SHOW;
  }

  @GetMapping("/posts/save")
  public String postsSave(Model model, HttpSession httpSession) {
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    model.addAttribute("author", sessionUser);
    return PathUtil.POSTS_SAVE;
  }

  @GetMapping("/posts/update/{Id}")
  public String postsUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (!ResultsCheckUtil.loginCheckResult().isValid()) {
      return PathUtil.REDIRECT_TO_USERS_LOGIN;
    }
//    if (HttpSessionUtil.notLoggedIn(httpSession)) {
//      return PathUtil.REDIRECT_TO_USERS_LOGIN;
//    }
    PostsResponseDto responseDto = postsService.findById(Id);
    if (!ResultsCheckUtil.userValidCheckResult(Id).isValid()) {
      return PathUtil.INVALID_ACCESS;
    }
//    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
//    if (!sessionUser.matchId(Id)) {
//      return PathUtil.INVALID_ACCESS;
//    }
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    model.addAttribute("posts", responseDto);
    model.addAttribute("author", sessionUser);
    return PathUtil.POSTS_UPDATE;
  }

  @GetMapping("/answers/update/{Id}")
  public String answersUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (!ResultsCheckUtil.loginCheckResult().isValid()) {
      return PathUtil.REDIRECT_TO_USERS_LOGIN;
    }
//    if (HttpSessionUtil.notLoggedIn(httpSession)) {
//      return PathUtil.REDIRECT_TO_USERS_LOGIN;
//    }
    AnswersResponseDto responseDto = answersService.findById(Id);
    if (!ResultsCheckUtil.userValidCheckResult(Id).isValid()) {
      return PathUtil.INVALID_ACCESS;
    }
//    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
//    if (!sessionUser.matchId(Id)) {
//      return PathUtil.INVALID_ACCESS;
//    }
    model.addAttribute("answers", responseDto);
    return PathUtil.ANSWERS_UPDATE;
  }

  @GetMapping("/users/login")
  public String usersLogin() {
    return PathUtil.USERS_LOGIN;
  }

  @GetMapping("/users/login/fail")
  public String usersLoginFailed() {
    return PathUtil.USERS_LOGIN_FAILED;
  }

  @GetMapping("/users/register")
  public String usersRegister() {
    return PathUtil.USERS_REGISTER;
  }

  @GetMapping("/users/show")
  public String usersShow(Model model) {
    model.addAttribute("users", usersService.findAllDesc());
    return PathUtil.USERS_SHOW;
  }

  @GetMapping("/users/update/{Id}")
  public String usersUpdate(@PathVariable Long Id, Model model, HttpSession httpSession) {
    if (!ResultsCheckUtil.loginCheckResult().isValid()) {
      return PathUtil.REDIRECT_TO_USERS_LOGIN;
    }
//    if (HttpSessionUtil.notLoggedIn(httpSession)) {
//      return PathUtil.REDIRECT_TO_USERS_LOGIN;
//    }
    if (!ResultsCheckUtil.userValidCheckResult(Id).isValid()) {
      return PathUtil.INVALID_ACCESS;
    }
//    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
//    if (!sessionUser.getId().equals(Id)) {
//      return PathUtil.INVALID_ACCESS;
//    }
    UsersResponseDto responseDto = usersService.findById(Id);
    model.addAttribute("user", responseDto);
    return PathUtil.USERS_UPDATE;
  }
}