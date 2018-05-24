package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void okTest() {
        Result resultOk = Result.ok();
        assertThat(resultOk.isValid(), is(true));
        assertNull(resultOk.getErrorMessage());
    }

    @Test
    public void failTest() {
        Result resultFail = Result.fail("Fail Test");
        assertThat(resultFail.isValid(), is(false));
        assertThat(resultFail.getErrorMessage(), is("Fail Test"));
    }
}