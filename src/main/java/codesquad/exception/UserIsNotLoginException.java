package codesquad.exception;

public class UserIsNotLoginException extends RuntimeException {

    public UserIsNotLoginException() {
    }

    public UserIsNotLoginException(String message) {
        super(message);
    }
}
