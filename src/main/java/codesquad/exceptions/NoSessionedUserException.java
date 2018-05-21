package codesquad.exceptions;

public class NoSessionedUserException extends RuntimeException {

    public NoSessionedUserException() {
    }

    public NoSessionedUserException(String message) {
        super(message);
    }

    public NoSessionedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionedUserException(Throwable cause) {
        super(cause);
    }
}
