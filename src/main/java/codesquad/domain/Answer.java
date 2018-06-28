package codesquad.domain;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity{
	@Embedded
	@JsonProperty
	private ClassForQAndA classForQAndA;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question")) 
	@JsonProperty
	private Question question;
	@Lob
	@JsonProperty
	private String contents;
	
	public Answer() {
		
	}
	
	public Answer(User writer, Question question, String contents) {
		this.classForQAndA = new ClassForQAndA(writer);
		this.question = question;
		this.contents = contents;
	}
	
	public void update(String contents) {
		this.contents = contents;
	}
	
	public ClassForQAndA classForQAndA() {
		return this.classForQAndA;
	}

	@Override
	public String toString() {
		return "Answer [" + super.toString() + "writer=" + classForQAndA.writer + ", question=" + question + ", contents=" + contents + "]";
	}

	
}
