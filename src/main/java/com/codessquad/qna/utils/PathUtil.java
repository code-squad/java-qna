package com.codessquad.qna.utils;

public class PathUtil {
  private static final String REDIRECT = "redirect:";
  public static final String REDIRECT_TO_MAIN = REDIRECT + "/";
  public static final String POSTS_SAVE = "/posts/posts-save";
  public static final String POSTS_UPDATE = "/posts/posts-update";
  public static final String POSTS_SHOW = "/posts/posts-show";
  public static final String USERS_LIST = "/users/users-show";
  public static final String USERS_LOGIN = "/users/users-login";
  public static final String INDEX = "index";
  public static final String ANSWERS_UPDATE = "/answers/answers-update";

  public static final String REDIRECT_TO_USERS_LOGIN = REDIRECT + "/users/login";
  public static final String USERS_REGISTER = "/users/users-register";
  public static final String USERS_SHOW = "/users/users-show";
  public static final String USERS_UPDATE = "/users/users-update";

  //error
  public static final String USERS_LOGIN_FAILED = REDIRECT + "users-login-failed";
  public static final String NO_SUCH_USERS = REDIRECT + "users-nonexistence";
  public static final String INVALID_ACCESS = REDIRECT + "users-update-invalid-access";
  public static final String NO_SUCH_POSTS_OR_ANSWERS = REDIRECT + "posts-nonexistence";
}