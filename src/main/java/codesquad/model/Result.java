package codesquad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private static final Result successful = new Result(true, "Successful!");
    private static final Result failed = new Result(false, "Failed!");

    @JsonProperty
    private String message;
    @JsonProperty
    private boolean success;

    private Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static Result ofSuccess() {
        return successful;
    }

    public static Result ofFailure() {
        return failed;
    }
}
