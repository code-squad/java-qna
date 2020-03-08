package com.codessquad.qna.common.error.exception;

public class CannotEditOtherUserInfoException extends BusinessException {

    public CannotEditOtherUserInfoException() {
        super(ErrorCode.CANNOT_EDIT_OTHER_USER_INFO);
    }

    public CannotEditOtherUserInfoException(String message) {
        super(message, ErrorCode.CANNOT_EDIT_OTHER_USER_INFO);
    }
}
