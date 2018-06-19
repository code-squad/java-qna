package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length=20)
	private String userId;
	private String password;
	private String name;
	private String email;

	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

}
