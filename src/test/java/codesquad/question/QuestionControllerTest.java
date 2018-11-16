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