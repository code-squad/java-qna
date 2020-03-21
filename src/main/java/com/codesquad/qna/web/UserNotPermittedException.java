package com.codesquad.qna.web;

public class UserNotPermittedException extends RuntimeException {
    // statusCode - 401 or 403

    public UserNotPermittedException() {
        super("권한이 없습니다. 권한을 가지고 있는 사용자일 경우 로그인 인증 후 다시 시도 해주세요.");
    }

    public UserNotPermittedException(String errorMessage) {
        super(errorMessage);
    }
}
