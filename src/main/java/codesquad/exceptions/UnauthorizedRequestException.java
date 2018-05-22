package codesquad.exceptions;

public class UnauthorizedRequestException extends RuntimeException {

    public UnauthorizedRequestException() {
    }

    public UnauthorizedRequestException(String messeage) {
        super(messeage);
    }

    public UnauthorizedRequestException(String messeage, Throwable cause) {
        super(messeage, cause);
    }

    public UnauthorizedRequestException(Throwable cause) {
        super(cause);
    }
}
