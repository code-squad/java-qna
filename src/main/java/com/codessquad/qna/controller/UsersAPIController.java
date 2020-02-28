package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Users;
import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.dto.UsersRegisterRequestDto;
import com.codessquad.qna.web.dto.UsersUpdateRequestDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsersAPIController {

  private final UsersService usersService;
  private final UsersRepository usersRepository;

  @PostMapping("/api/v1/users")
  public Long register(@RequestBody UsersRegisterRequestDto requestDto) {
    return usersService.register(requestDto);
  }

  @GetMapping("/api/v1/users/list")
  public String index(Model model) {
    model.addAttribute("users", usersService.findAllDesc());
    return "users-show";
  }

  @PutMapping("/api/v1/users/{Id}")
  public Long update(@PathVariable Long Id, @RequestBody UsersUpdateRequestDto requestDto) {
    return usersService.update(Id, requestDto);
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
    session.setAttribute("user", user);
    System.out.println("login success");
    return "index";
  }
}