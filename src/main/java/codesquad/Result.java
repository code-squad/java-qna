package codesquad;

public class Result {
    private boolean valid;

    private String errorMassage;

    private Object object;

    private Result(boolean valid, String errorMassage) {
        this.valid = valid;
        this.errorMassage = errorMassage;
    }

    private Result(boolean valid, String errorMassage, Object object) {
        this.valid = valid;
        this.errorMassage = errorMassage;
        this.object = object;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result ok(Object object) {
        return new Result(true, null, object);
    }

    public static Result fail(String errorMassage) {
        return new Result(false, errorMassage);
    }

    public Object getObject() {
        return object;
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
