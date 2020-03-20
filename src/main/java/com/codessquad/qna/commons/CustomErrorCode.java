package com.codessquad.qna.commons;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {
  USER_NOT_EXIST("존재하지 않는 User id 입니다."),
  USER_NOT_LOGIN("Login 이 필요합니다."),
  USER_NOT_MATCHED_PASSWORD("비밀번호가 틀립니다."),
  USER_NOT_MATCHED("일치하지 않는 User 입니다."),

  QUESTION_NOT_EXIST("존재하지 않는 Question id 입니다."),
  ANSWER_NOT_EXIST("존재하지 않는 Answer id 입니다."),

  BAD_REQUEST(HttpStatus.BAD_REQUEST.getReasonPhrase());

  private final String msg;

  CustomErrorCode(String msg) {
    this.msg = msg;
  }
}
