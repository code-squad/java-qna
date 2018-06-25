package codesquad.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ClassForQAndA {
	@ManyToOne
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