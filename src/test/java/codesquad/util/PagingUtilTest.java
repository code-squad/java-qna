package codesquad.util;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PagingUtilTest {

    @Test
    public void 뒤쪽_버튼만_있을_때() {
        PagingUtil pu = new PagingUtil(24, 3);
        assertThat(pu.isPrevButton()).isFalse();
        assertThat(pu.isNextButton()).isTrue();
        assertThat(pu.getPagingNumbers()).isEqualTo(Arrays.asList(1, 2, 3, 4, 5));
    }

    @Test
    public void 두_버튼_있을_때() {
        PagingUtil pu = new PagingUtil(27, 7);
        assertThat(pu.isPrevButton()).isTrue();
        assertThat(pu.isNextButton()).isTrue();
        assertThat(pu.getPagingNumbers()).isEqualTo(Arrays.asList(6, 7, 8, 9, 10));
    }

    @Test
    public void 앞_버튼만_있을_때(){
        PagingUtil pu = new PagingUtil(30, 26);
        assertThat(pu.isPrevButton()).isTrue();
        assertThat(pu.isNextButton()).isFalse();
        assertThat(pu.getPagingNumbers()).isEqualTo(Arrays.asList(26, 27,28,29,30));
    }

    @Test
    public void 페이지바를_채우지못하면서_앞쪽_버튼_없을_때() {
        PagingUtil pagingUtil = new PagingUtil(4, 2);
        assertThat(pagingUtil.isPrevButton()).isFalse();
        assertThat(pagingUtil.isNextButton()).isFalse();
        assertThat(pagingUtil.getPagingNumbers()).isEqualTo(Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void 페이지바를_채우지_못하면서_앞쪽_버튼_있을_때() {
        PagingUtil pu = new PagingUtil(27, 26);
        assertThat(pu.isPrevButton()).isTrue();
        assertThat(pu.isNextButton()).isFalse();
        assertThat(pu.getPagingNumbers()).isEqualTo(Arrays.asList(26, 27));
    }
}