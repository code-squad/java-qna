package com.codessquad.qna.service.users;

import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.web.dto.UsersListResponseDto;
import com.codessquad.qna.web.dto.UsersRegisterRequestDto;
import com.codessquad.qna.web.dto.UsersResponseDto;
import com.codessquad.qna.web.dto.UsersUpdateRequestDto;
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

  @Transactional(readOnly = true)
  public UsersResponseDto findById(Long id) {
    Users entity = usersRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("No Such User.")
    );
    return new UsersResponseDto(entity);
  }

  @Transactional
  public Long register(UsersRegisterRequestDto requestDto) {
    return usersRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, UsersUpdateRequestDto requestDto) {
    Users users = usersRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("No Such User." + " id " + id));
    users.update(requestDto.getUserId(), requestDto.getPassword(), requestDto.getName(), requestDto.getEmail());
    return id;
  }
}
