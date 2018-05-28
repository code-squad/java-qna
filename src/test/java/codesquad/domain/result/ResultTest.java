package codesquad.domain.result;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void ok() {
        assertTrue(Result.ok().isValid());
    }

    @Test
    public void fail() {
        assertFalse(Result.fail("fail").isValid());
    }

    @Test
    public void ok_err_messeage() {
        assertNull(Result.ok().getErrorMessage());
    }

    @Test
    public void fail_err_messeage() {
        assertNotNull(Result.fail("fail").getErrorMessage());
    }
}
