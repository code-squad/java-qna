package com.codessquad.qna.errors;

import com.codessquad.qna.commons.CustomErrorCode;

public class QuestionException extends RuntimeException {

  public QuestionException(CustomErrorCode customErrorCode) {
    super(customErrorCode.getMsg());
  }
}
