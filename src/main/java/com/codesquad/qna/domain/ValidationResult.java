package com.codesquad.qna.domain;

public class ValidationResult {
    private boolean valid;

    private String errorMessage;

    private ValidationResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ValidationResult ok() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult fail(String errorMessage) {
        return new ValidationResult(false, errorMessage);
    }
}
