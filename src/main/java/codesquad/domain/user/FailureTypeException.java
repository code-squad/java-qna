package codesquad.domain.user;

public class FailureTypeException extends RuntimeException {
    private String message;

    public FailureTypeException(String message) {
        super(message);
    }
}
