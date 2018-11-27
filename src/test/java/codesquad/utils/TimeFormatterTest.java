package codesquad.utils;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeFormatterTest {

    @Test
    public void commonFormat() {
        System.out.println(TimeFormatter.commonFormat(LocalDateTime.now()));
    }
}