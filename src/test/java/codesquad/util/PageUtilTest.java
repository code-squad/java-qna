package codesquad.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class PageUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(PageUtilTest.class);

    @Test
    public void 현재페이지에서다섯개() {
        PageUtil pageUtil = new PageUtil(11, 11);
        for (Integer currentPage : pageUtil.getPages()) {
            logger.debug("Current Page : {}", currentPage);
        }
    }

    @Test
    public void 전다섯개의첫번째로가기() {
        logger.debug("PreFirstPages : {}", new PageUtil(18, 11).getPreFirstPage());
        assertNull(new PageUtil(10, 4).getPreFirstPage());
    }

    @Test
    public void 다음다섯개의첫번째로가기() {
        logger.debug("NextFirstPage : {}", new PageUtil(15, 6).getNextFirstPage());
        assertNull(new PageUtil(15, 11).getNextFirstPage());
    }
}