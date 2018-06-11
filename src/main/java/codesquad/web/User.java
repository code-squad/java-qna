package codesquad.web;

public class User {
	private String userId;
	private String passsword;
	private String name;
	private String email;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", passsword=" + passsword + ", name=" + name + ", email=" + email + "]";
	}
}
