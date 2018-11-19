package codesquad.qna;

import java.util.Date;
import java.util.List;

public class Question {
    private Integer no;
    private String writer;
    private String title;
    private String contents;
    private String reportingDate;
    private List<Comment> comments;

    public Question(Integer no, String writer, String title, String contents, String reportingDate, List<Comment> comments) {
        this.no = no;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.reportingDate = reportingDate;
        this.comments = comments;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public String getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", reportingDate='" + reportingDate + '\'' +
                ", comments=" + comments +
                '}';
    }

    public boolean isQuestion(int no) {
        return this.no == no;
    }
}
