package codesquad.qna;

public class Result {
    private boolean valid; // 유효성
    private String errorMessage;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result failed(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
