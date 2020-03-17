package com.codesquad.qna.advice.exception;

public class InputMistakeException extends RuntimeException {
    public InputMistakeException(String message) {
        super(message);
    }

    public static InputMistakeException enteredNull() {
        return new InputMistakeException("Null 값을 입력하였습니다");
    }
}
