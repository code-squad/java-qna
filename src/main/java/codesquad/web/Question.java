package codesquad.web;

public class Question {
    private int id;
    private String writer;
    private String title;
    private String contents;

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return writer + " " + title + " " + contents;
    }

    public boolean isValidId(String userId) {
        return String.valueOf(this.id).equals(userId);
    }
}
