package codesquad.domain.user;

public class DuplicationException extends RuntimeException {
    private String message;

    public DuplicationException(String message) {
        super(message);
    }
}
