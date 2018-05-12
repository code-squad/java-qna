package codesquad.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Question implements Comparable<Question> {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public Question() {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN).format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    @Override
    public int compareTo(Question otherQuestion) {
        return Integer.compare(otherQuestion.id, this.id);
    }
}
