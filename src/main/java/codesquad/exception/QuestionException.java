package codesquad.exception;

public class QuestionException extends RuntimeException {
    public QuestionException() {
    }

    public QuestionException(String message) {
        super(message);
    }
}
