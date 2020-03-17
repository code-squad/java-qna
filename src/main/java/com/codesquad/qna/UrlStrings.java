package com.codesquad.qna;

public enum UrlStrings {
    REDIRECT_MAIN("redirect:/"),
    REDIRECT_LOGIN_FORM("redirect:/users/login"),
    REDIRECT_USERS_DATA("redirect:/users");

    private final String url;

    UrlStrings(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
