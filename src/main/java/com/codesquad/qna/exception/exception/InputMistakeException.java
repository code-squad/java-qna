package com.codesquad.qna.exception.exception;

public class InputMistakeException extends RuntimeException {
    public InputMistakeException(String message) {
        super(message);
    }

    public static InputMistakeException existUser() {
        return new InputMistakeException("이미 존재하는 아이디입니다");
    }

    public static InputMistakeException noMatchUser() {
        return new InputMistakeException("일치하는 사용자가 존재하지 않습니다");
    }

    public static InputMistakeException noMatchPassword() {
        return new InputMistakeException("비밀번호가 일치하지 않습니다");
    }
}
