package com.codessquad.qna.errors;

import com.codessquad.qna.commons.CustomErrorCode;

public class UserException extends RuntimeException {

  public UserException(CustomErrorCode customErrorCode) {
    super(customErrorCode.getMsg());
  }
}
