package com.codessquad.qna.common.error;

import com.codessquad.qna.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorResponse {

    private int status;
    private String message;
    private List<FieldError> errors;
    private String code;
    private String stackTrace;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.code = code.getCode();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public String getCode() {
        return code;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String toHtmlString() {
        return "<html><head><title>[" +
                this.status +
                "] " +
                HttpStatus.valueOf(this.status).getReasonPhrase() +
                "</title></head>" +
                "<body><h1>Whitelabel Error Page</h1>" +
                "<img src=\"https://i.imgur.com/aBJdxJz.png\" alt=\"어피치\" width=\"300\" height=\"300\"><p>" +
                this.message +
                "</p><pre>" +
                this.stackTrace +
                "</pre></body></html>";
    }

    public static class FieldError {

        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                              .map(error -> new FieldError(
                                      error.getField(),
                                      error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                                      error.getDefaultMessage()))
                              .collect(Collectors.toList());
        }
    }
}
