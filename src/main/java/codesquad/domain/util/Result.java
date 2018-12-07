package codesquad.domain.util;

public class Result {
    private boolean valid;
    private String errorMessage;
    private Object transfer;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    private Result(boolean valid, String errorMessage, Object transfer) {
        this(valid, errorMessage);
        this.transfer = transfer;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result ok(Object transfer) {
        return new Result(true, null, transfer);
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
