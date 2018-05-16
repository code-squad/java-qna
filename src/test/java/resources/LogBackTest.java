package resources;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackTest {

    static final Logger Log = LoggerFactory.getLogger(LogBackTest.class);

    @Test
    public void testLogger() {
        Log.info("UserName is {}, Email is {} ", "larry", "abc@gmail.com");
    }
}
