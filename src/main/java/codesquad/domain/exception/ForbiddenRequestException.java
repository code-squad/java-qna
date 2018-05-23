package codesquad.domain.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenRequestException extends CustomException {

    public ForbiddenRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
