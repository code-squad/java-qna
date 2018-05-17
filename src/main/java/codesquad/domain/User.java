package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false, length = 20, unique = true)
	@JsonProperty
	private String userId;
	@JsonIgnore
	private String password;
	public String getPassword() {
		return password;
	}

	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20)
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User update(User newUser) {
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;
		return this;
	}


	public Boolean matchId(Long newId) {
		if (newId == null) {
			return false;
		}
		return newId.equals(getId());
	}
	
	public Boolean matchPassword(String newPassword) {
		if (newPassword == null) {
			return false;
		}
		return newPassword.equals(password);
	}

	@Override
	public String toString() {
		return "User [" + super.toString() + "userId=" + userId + ", password=" + password + ", name=" + name
				+ ", email=" + email + "]";
	}

}
