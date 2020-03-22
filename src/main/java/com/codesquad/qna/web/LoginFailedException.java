package com.codesquad.qna.web;

public class LoginFailedException extends RuntimeException {
    // statusCode - 400

    public LoginFailedException() {
        super("비밀번호가 틀립니다. 다시 입력 해주세요.");
    }

    public LoginFailedException(String errorMessage) {
        super(errorMessage);
    }

}
