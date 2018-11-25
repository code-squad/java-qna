package codesquad.exception;

public class UpdatefailedException extends RuntimeException {
    public UpdatefailedException() {
    }

    public UpdatefailedException(String message) {
        super(message);
    }
}
