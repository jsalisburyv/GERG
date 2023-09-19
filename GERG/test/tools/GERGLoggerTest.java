package tools;

import utils.GERGLogger;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elsho
 */
public class GERGLoggerTest {

    @Test
    public void testInit() {
        System.out.println("init");
        GERGLogger.init();
        Logger logger = Logger.getLogger("") ;
        assertFalse(logger == null);
        assertTrue(logger.getHandlers().length == 2);
    }

    /**
     * Test of init method, of class GERGLogger.
     */
    @Test
    public void testInit_String() {
        System.out.println("init string");
        String filepath = "test/log.txt";
        GERGLogger.init(filepath);
        Logger logger = Logger.getLogger("") ;
        assertFalse(logger == null);
        assertTrue(logger.getHandlers().length == 2);
    }
    
}
