package codesquad.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity{
	@Column(nullable=false, length=20, unique=true)
	@JsonProperty
	private String userId;
	@JsonIgnore
	private String password;
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String passsword) {
		this.password = passsword;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public boolean checkPassword(String insertedPassword) {
		if (insertedPassword == null) {
			return false;
		}
		return insertedPassword.equals(password);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void update(User newUser) {
		this.email = newUser.email;
		this.name = newUser.name;
		this.password = newUser.password;
	}
	
	public boolean checkId(Long insertedId) {
		if (insertedId == null) {
			return false;
		}
		return insertedId.equals(getId());
	}

	@Override
	public String toString() {
		return "User [" + super.toString() + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}
}
