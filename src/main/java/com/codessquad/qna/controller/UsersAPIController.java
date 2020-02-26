package com.codessquad.qna.controller;

import com.codessquad.qna.service.users.UsersService;
import com.codessquad.qna.web.dto.UsersRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsersAPIController {

  private final UsersService usersService;

  @PostMapping("/api/v1/users")
  public Long register(@RequestBody UsersRegisterRequestDto requestDto) {
    return usersService.register(requestDto);
  }
}