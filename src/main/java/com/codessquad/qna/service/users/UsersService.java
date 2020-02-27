package com.codessquad.qna.service.users;

import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.web.dto.UsersListResponseDto;
import com.codessquad.qna.web.dto.UsersRegisterRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
  private final UsersRepository usersRepository;

  public List<UsersListResponseDto> findAllDesc() {
    return usersRepository.findAllDesc().stream()
        .map(posts -> new UsersListResponseDto(posts))
        .collect(Collectors.toList());
  }

  @Transactional
  public Long register(UsersRegisterRequestDto requestDto) {
    return usersRepository.save(requestDto.toEntity()).getId();
  }
}
