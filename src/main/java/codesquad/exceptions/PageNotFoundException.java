package codesquad.exceptions;

public class PageNotFoundException extends RuntimeException {

    public PageNotFoundException() {
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageNotFoundException(Throwable cause) {
        super(cause);
    }
}
