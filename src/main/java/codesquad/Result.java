package codesquad;

public class Result {
    private boolean valid;

    private String errorMassage;

    private Result(boolean valid, String errorMassage) {
        this.valid = valid;
        this.errorMassage = errorMassage;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String errorMassage) {
        return new Result(false, errorMassage);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMassage() {
        return errorMassage;
    }

    public void setErrorMassage(String errorMassage) {
        this.errorMassage = errorMassage;
    }
}
