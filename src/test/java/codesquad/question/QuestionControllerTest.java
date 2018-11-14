package codesquad.question;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionControllerTest {

    @Test
    public void 날짜_데이터_출력() {
        Date today = new Date();
        System.out.println(today);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        System.out.println(date.format(today) + time.format(today));
    }

    @Test
    public void 싱글톤_연습() {
        System.out.println(Something.getInstance());
        Something.getInstance().add(new Question(1, "brad", "질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-01-12 13:12"));
        System.out.println(Something.getInstance());

        Something.getInstance().add(new Question(2, "brad", "질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-01-12 13:12"));
        System.out.println(Something.getInstance());

        test();
        System.out.println(Something.getInstance());

        List<Question> question = Something.getInstance();
        question.add(new Question(4, "david", "질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-01-12 13:12"));
        System.out.println(Something.getInstance());
    }

    public void test() {
        Something.getInstance().add(new Question(3, "brad", "질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-01-12 13:12"));
    }
}

class Something {
    private Something() {}

    private static class LazyHolder {
        public static final List<Question> instance = new ArrayList<>();
    }

    public static List<Question> getInstance() {
        return LazyHolder.instance;
    }
}