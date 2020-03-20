package com.codessquad.qna.service.users;

import com.codessquad.qna.controller.users.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.exception.NoSuchUserException;
import com.codessquad.qna.utils.PathUtil;
import com.codessquad.qna.web.dto.users.UsersListResponseDto;
import com.codessquad.qna.web.dto.users.UsersRegisterRequestDto;
import com.codessquad.qna.web.dto.users.UsersResponseDto;
import com.codessquad.qna.web.dto.users.UsersUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {
  private final UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public List<UsersListResponseDto> findAllDesc() {
    return usersRepository.findAllDesc().stream()
        .map(posts -> new UsersListResponseDto(posts))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public UsersResponseDto findById(Long id) {
    Users entity = checkValidity(id);
    return new UsersResponseDto(entity);
  }

  @Transactional
  public Users register(UsersRegisterRequestDto requestDto) {
    return usersRepository.save(requestDto.toEntity());
  }

  @Transactional
  public Users update(Long id, UsersUpdateRequestDto requestDto) {
    Users entity = checkValidity(id);
    return entity.update(requestDto.getUserId(), requestDto.getPassword(), requestDto.getName(), requestDto.getEmail());
  }

  private Users checkValidity(Long id) {
    Users users = usersRepository.findById(id).orElseThrow(
        () -> new NoSuchUserException(PathUtil.NO_SUCH_USERS, "No Such User." + " id " + id));
    // TODO Users 회원탈퇴 기능 구현하기 (isDeleted()를 사용할 수 있도록 설계해보기)
    return users;
  }
}
