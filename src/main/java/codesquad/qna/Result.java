package codesquad.qna;

public class Result {
    private boolean valid; // 유효성
    private String errorMessage;
    private Object object;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    private Result(boolean valid, String errorMessage, Object object) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.object = object;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getObject() {
        return object;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result ok(Object object) {
        return new Result(true, null, object);
    }

    public static Result failed(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
