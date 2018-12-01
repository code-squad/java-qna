package codesquad.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private String message;
    @JsonProperty
    private boolean valid;
    public Result() {
        message = null;
        valid = true;
    }
    public Result(String message) {
        this.message = message;
        valid = false;
    }
    public static Result ok() {
        return new Result();
    }
    public static Result error(String message) {
        return new Result(message);
    }
    public String getMessage() {
        return this.message;
    }
    public boolean getValid() {
        return valid;
    }
}