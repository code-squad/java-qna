package com.codesquad.qna.web;

public class UserNotFoundException extends RuntimeException {
    // statusCode - 404

    public UserNotFoundException() {
        super("존재하지 않는 사용자 입니다. 회원 가입을 하거나 로그인을 해주세요.");
    }

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
