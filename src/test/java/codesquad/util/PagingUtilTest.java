package codesquad.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PagingUtilTest {

    private PagingUtil pagingUtil;

    @Test
    public void test() {
        pagingUtil = new PagingUtil(20, 2, 5);
        assertThat(pagingUtil.isPrevButton()).isFalse();
        assertThat(pagingUtil.isNextButton()).isTrue();
        assertThat(pagingUtil.getPagingNumbers()).isEqualTo(Arrays.asList(1, 2, 3, 4, 5));
    }

    @Test
    public void test1() {
        PagingUtil pu = new PagingUtil(24, 3, 27);
        assertThat(pu.isNextButton()).isFalse();
        assertThat(pu.isPrevButton()).isFalse();
        assertThat(pu.getPagingNumbers()).isEqualTo(
                IntStream.rangeClosed(1, 24).boxed().collect(Collectors.toList())
        );
    }

    @Test
    public void test2() {
        PagingUtil pu = new PagingUtil(27, 7, 4);
        assertThat(pu.isNextButton()).isTrue();
        assertThat(pu.isPrevButton()).isTrue();
        assertThat(pu.getPagingNumbers()).isEqualTo(Arrays.asList(5, 6, 7, 8));
    }

}