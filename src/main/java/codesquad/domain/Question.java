package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@ToString
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public Question() {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN).format(new Date());
    }
}
