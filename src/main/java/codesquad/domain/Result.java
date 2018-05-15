package codesquad.domain;

public class Result {
	private boolean valid;
	
	private String errorMessage;
	
	public Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
	
	public boolean isValid() {
		return valid;
	}
	
} 
