package codesquad.domain.util;

public class SessionMaintenanceException extends RuntimeException {
    private String message;

    public SessionMaintenanceException(String message) {
        super(message);
    }
}
