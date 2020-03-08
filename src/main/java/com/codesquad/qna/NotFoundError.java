package com.codesquad.qna;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundError extends NotFoundException {
    public static final String NOT_FOUND_MESSAGE = "페이지가 존재하지 않습니다";

    public NotFoundError(String notFoundMessage) {
        super(notFoundMessage);
        printErrorMessage();
    }

    public void printErrorMessage() {
        printStackTrace();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, getMessage());
    }
}
