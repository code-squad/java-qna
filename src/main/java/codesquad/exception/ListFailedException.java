package codesquad.exception;

public class ListFailedException extends RuntimeException {
    public ListFailedException() {
    }

    public ListFailedException(String message) {
        super(message);
    }
}
