package codesquad.exception;

public class AnswerIdNotMatchException extends RuntimeException {
    public AnswerIdNotMatchException() {
    }

    public AnswerIdNotMatchException(String message) {
        super(message);
    }
}
