package codesquad.exception;

public class LoginUseIsNotMatchException extends RuntimeException {
    public LoginUseIsNotMatchException() {
    }

    public LoginUseIsNotMatchException(String message) {
        super(message);
    }
}
