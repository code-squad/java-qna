package codesquad.domain.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
