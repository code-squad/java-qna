package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Result {
    SUCCESS(0, true, null),
    NEED_LOGIN(1, false, "로그인이 필요합니다."),
    MISMATCH_USER(2, false, "사용자 ID가 일치하지 않습니다."),
    MISMATCH_ID(3, false, "정상적인 아이디를 입력해주세요."),
    MISMATCH_PWD(4, false, "비밀번호가 일치하지 않습니다.");

    int code;
    boolean valid;
    String message;

    private Result(int code, boolean valid, String message) {
        this.code = code;
        this.valid = valid;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return this.valid;
    }

    public boolean getValid() {
        return this.valid;
    }

    @Override
    public String toString() {
        return code + " : " + message;
    }
}
