package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Question{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long id;
	@Embedded
	@JsonProperty
	private ClassForQAndA classForQAndA;
	@OneToMany(mappedBy="question")
	@OrderBy("id DESC")
	private List<Answer> answers;
	@JsonProperty
	private String title;
	@Lob
	@JsonProperty
	private String contents;
	@JsonProperty
	private LocalDateTime createDate;
	
	public Question() {
		
	}
	
	public Question(User writer, String title, String contents) {
		super();
		this.classForQAndA = new ClassForQAndA(writer);
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public String getFormattedCreateDate() {
		if (createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + classForQAndA.writer + ", title=" + title + ", contents=" + contents + "]";
	}
}
