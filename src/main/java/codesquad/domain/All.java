package codesquad.domain;

public class All {
	private User writer;
	
	public boolean checkWriter(User user) {
		return writer.equals(user);
	}
}
