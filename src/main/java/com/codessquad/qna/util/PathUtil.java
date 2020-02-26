package com.codessquad.qna.util;

public class PathUtil {
    private static final String REDIRECT = "redirect:";
    //home
    public static final String HOME = REDIRECT + "/";
    //user
    public static final String USER_LIST = REDIRECT + "/users";
    public static final String USER_LIST_TEMPLATE = "user/list";
    public static final String USER_PROFILE_TEMPLATE = "user/profile";
    public static final String USER_EDIT_TEMPLATE = "user/edit";
    //error
    public static final String NOT_FOUND = REDIRECT + "/error/notFond";
    public static final String BAD_REQUEST = REDIRECT + "/error/badRequest";
    public static final String UNAUTHORIZED = REDIRECT + "/error/unauthorized";
    public static final String LOGIN_FAILED_TEMPLATE = "error/user/login_failed";
}
