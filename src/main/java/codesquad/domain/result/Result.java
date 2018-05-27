package codesquad.domain.result;

public class Result {
    private static final Result SUCCESS = new Result(true, null);

    private boolean valid;
    private String errorMessage;

    private Result(boolean valid, String message) {
        this.valid = valid;
        this.errorMessage = message;
    }

    public static Result ok() {
        return SUCCESS;
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid() {
        return valid;
    }
}
