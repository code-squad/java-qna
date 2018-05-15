package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@ToString
public class Question implements Comparable<Question> {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public Question() {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN).format(new Date());
    }

    @Override
    public int compareTo(Question otherQuestion) {
        return Integer.compare(otherQuestion.id, this.id);
    }
}
