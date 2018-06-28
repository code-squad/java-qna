package codesquad.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Question extends AbstractEntity{
	@Embedded
	@JsonProperty
	private ClassForQAndA classForQAndA;
	@OneToMany(mappedBy="question")
	@OrderBy("id DESC")
	private List<Answer> answers;
	@JsonProperty
	private String title;
	@JsonProperty
	private Integer countOfAnswer = 0;
	@Lob
	@JsonProperty
	private String contents;
	
	public Question() {
		
	}
	
	public Question(User writer, String title, String contents) {
		super();
		this.classForQAndA = new ClassForQAndA(writer);
		this.title = title;
		this.contents = contents;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public ClassForQAndA getClassForQAndA() {
		return classForQAndA;
	}
	
	public void addAnswer() {
		++this.countOfAnswer;
	}
	
	public void delteAnswer() {
		--this.countOfAnswer;
	}
	
	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Question [" + super.toString() + ", writer=" + classForQAndA.writer + ", title=" + title + ", contents=" + contents + "]";
	}

}
