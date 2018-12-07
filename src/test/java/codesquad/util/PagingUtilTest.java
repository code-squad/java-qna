package codesquad.util;

import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class PagingUtilTest {
    private static final Logger logger = getLogger(PagingUtilTest.class);

    @Test
    public void getNextPage() {
        for(int i = 0; i < 5; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertEquals((Integer)5, pu.getNextPage());
        }

        for(int i = 5; i < 10; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertEquals((Integer)10, pu.getNextPage());
        }

        PagingUtil pu = new PagingUtil(10, 10);
        assertNull(pu.getNextPage());
    }

    @Test
    public void getPrePage() {
        for(int i = 0; i < 5; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertNull(pu.getPrePage());
        }

        for(int i = 5; i < 10; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertEquals((Integer)4, pu.getPrePage()) ;
        }

        PagingUtil pu = new PagingUtil(10, 10);
        assertEquals((Integer)9, pu.getPrePage());
    }

    @Test
    public void getPages() {
        for(int i = 0; i < 5; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertEquals(5, pu.getPages().size());
            System.out.println(pu.getPages());
        }

        for(int i = 5; i < 10; i++){
            PagingUtil pu = new PagingUtil(i, 10);
            logger.debug("cur page : {}" , i);
            assertEquals(5, pu.getPages().size());
            System.out.println(pu.getPages());
        }

        PagingUtil pu = new PagingUtil(10, 10);
        System.out.println(pu.getPages());
    }
}