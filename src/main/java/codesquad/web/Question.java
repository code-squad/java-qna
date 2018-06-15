package codesquad.web;


public class Question {
	private long id;
	private String writer;
	private String title;
	private String contents;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		return "Question [name=" + writer + ", title=" + title + ", content=" + contents + "]";
	}
}
