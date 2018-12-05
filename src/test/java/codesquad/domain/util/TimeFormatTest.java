package codesquad.domain.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeFormatTest {
    @Test
    public void getTimeFormatTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 30, 12, 11);
        assert(TimeFormat.getTimeFormat(localDateTime)).equals("2018-11-30 12:11");

        assertThat(new Long("3")).isEqualTo(new Long("3"));
    }
}
