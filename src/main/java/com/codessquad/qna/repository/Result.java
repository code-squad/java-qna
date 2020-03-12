package com.codessquad.qna.repository;

public class Result {
    private boolean valid;

    private String result;

    private Result(boolean valid, String result) {
        this.valid = valid;
        this.result = result;
    }

    public boolean isValid() {
        return valid;
    }

    public String getResult() {
        return result;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String result) {
        return new Result(false, result);
    }
}
