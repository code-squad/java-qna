package codesquad.domain;

import javax.servlet.http.HttpSession;

import codesquad.web.HttpSessionUtils;

public class Result {
	private boolean valid;
	private String errorMessage;
	
	public Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
	
	public static Result valid(HttpSession session, All all) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("u have to login");
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!all.checkWriter(sessionedUser)) {
			return Result.fail("u can modify & delete only yours");
		}
		return Result.ok();
	}
}
