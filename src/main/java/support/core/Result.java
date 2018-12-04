package support.core;

public class Result<T> {
    private T data;
    private boolean valid;
    private String errorMessage;

    private Result(T data, boolean valid, String errorMessage) {
        this.data = data;
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static Result ok(Object data){
        return new Result(data, true, "");
    }

    public static Result error(String errorMessage){
        return new Result(null, false, errorMessage);
    }

    public T getData() {
        return data;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
