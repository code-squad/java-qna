package com.codessquad.qna.controller.answers;

import com.codessquad.qna.service.answers.AnswersService;
import com.codessquad.qna.web.dto.answers.AnswersSaveRequestDto;
import com.codessquad.qna.web.dto.posts.AnswersUpdateRequestDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswersAPIController {
  private final AnswersService answersService;

  public AnswersAPIController(AnswersService answersService) {
    this.answersService = answersService;
  }

  //답변의 생성
  @PostMapping("/api/v1/questions")
  public Long save(@RequestBody AnswersSaveRequestDto requestDto) {
    return answersService.save(requestDto);
  }

  //답변의 업데이트
  @PutMapping("/api/v1/questions/{Id}")
  public Long update(@PathVariable Long Id, @RequestBody AnswersUpdateRequestDto requestDto) {
    return answersService.update(Id, requestDto);
  }
}
