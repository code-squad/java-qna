package codesquad.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import codesquad.web.HttpSessionUtils;


@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String writer;
	

	private String title;
	private String contents;
	private String time;

	
	public Question() {
	}
	
	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.time = new SimpleDateFormat("yyyy-mm-dd hh:mm").format(new Date(System.currentTimeMillis())); ;
	}
	
	public Boolean matchUserId(HttpSession session) {
		return writer.equals(HttpSessionUtils.getUserFromSession(session).getUserId());
	}
	
	public String getTime() {
		return time;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return "Question [writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}

	public Long getId() {
		return id;
	}

	public void update(String contents) {
		this.contents = contents;
	}

}
