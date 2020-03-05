package com.codessquad.qna.service.answers;

import com.codessquad.qna.controller.answers.AnswersRepository;
import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.web.dto.answers.AnswersListResponseDto;
import com.codessquad.qna.web.dto.answers.AnswersResponseDto;
import com.codessquad.qna.web.dto.answers.AnswersSaveRequestDto;
import com.codessquad.qna.web.dto.posts.AnswersUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswersService {

  private final AnswersRepository answersRepository;

  public AnswersService(AnswersRepository answersRepository) {
    this.answersRepository = answersRepository;
  }

  @Transactional(readOnly = true)
  public List<AnswersListResponseDto> findAllDesc() {
    return answersRepository.findAllDesc().stream()
        .map(answers -> new AnswersListResponseDto(answers))
        .collect(Collectors.toList());
  }

  @Transactional
  public Long save(AnswersSaveRequestDto requestDto) {
    return answersRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, AnswersUpdateRequestDto requestDto) {
    Answers answers = answersRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("no such post." + " id = " + id));
    answers.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  @Transactional(readOnly = true)
  public AnswersResponseDto findById(Long id) {
    Answers entity = answersRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no such post" + " id = " + id)
        );
    return new AnswersResponseDto(entity);
  }
}
