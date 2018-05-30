package codesquad.domain;

public class ResultBak {
    private boolean valid;
    private String errorMessage;

    private ResultBak(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static ResultBak ok() {
        return new ResultBak(true, null);
    }

    public static ResultBak fail(String errorMessage) {
        return new ResultBak(false, errorMessage);
    }
}
