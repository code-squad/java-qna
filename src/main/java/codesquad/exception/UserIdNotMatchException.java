package codesquad.exception;

public class UserIdNotMatchException extends RuntimeException {

    public UserIdNotMatchException() {
    }

    public UserIdNotMatchException(String message) {
        super(message);
    }
}
