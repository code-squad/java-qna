package com.codessquad.qna.controller.users;

import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.dto.users.UsersRegisterRequestDto;
import com.codessquad.qna.web.dto.users.UsersUpdateRequestDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersAPIController {

  private final UsersService usersService;
  private final UsersRepository usersRepository;

  public UsersAPIController(UsersService usersService, UsersRepository usersRepository) {
    this.usersService = usersService;
    this.usersRepository = usersRepository;
  }

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
}