package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long id;
	@Column(nullable=false, length=20, unique=true)
	@JsonProperty
	private String userId;
	@JsonIgnore
	private String password;
	@JsonProperty
	private String name;
	@JsonProperty
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
	
	public boolean checkId(Long insertedId) {
		if (insertedId == null) {
			return false;
		}
		return id.equals(insertedId);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}
}
