package com.codessquad.qna.common;

import java.time.format.DateTimeFormatter;

public class CommonConstants {
    public static final DateTimeFormatter POST_DATA_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
    public static final String ERROR_USER_NOT_FOUND = "error/user_not_found";
    public static final String ERROR_CANNOT_EDIT_OTHER_USER_INFO = "error/cannot_edit_other_user_info";
    public static final String SESSION_LOGIN_USER = "loginUser";
    public static final String ERROR_QUESTION_NOT_FOUND = "error/question_not_found";
    public static final String REDIRECT_LOGIN_PAGE = "redirect:/users/login";

    private CommonConstants() {}
}
