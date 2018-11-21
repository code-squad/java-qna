package codesquad.exception;

public class QuestionIdNotMatchException extends RuntimeException {
    public QuestionIdNotMatchException() {
    }

    public QuestionIdNotMatchException(String message) {
        super(message);
    }
}
