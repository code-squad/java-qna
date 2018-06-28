package codesquad.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class ClassForQAndA {
	@ManyToOne
	@JsonProperty
	protected User writer;
	
	public ClassForQAndA() {
		
	}
	
	public ClassForQAndA(User writer) {
		this.writer = writer;
	}

	public boolean checkWriter(User user) {
		return writer.equals(user);
	}
}