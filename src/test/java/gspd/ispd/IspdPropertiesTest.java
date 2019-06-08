package gspd.ispd;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;




/**
 * IspdPropertiesTest
 */
public class IspdPropertiesTest {
    
    @Test
    public void testLocale() {
        IspdSettings.load();
        Locale loc = IspdSettings.getLocale();
        assertFalse("locale is not NULL", loc == null);
        assertEquals("locale is right", loc, new Locale("en","US"));
    }

    @Test
    public void testWorkingDirectory() {
        try {
            IspdSettings.load();
            String wd = IspdSettings.getWorkingDirectory();
            assertFalse("working directory is not NULL", wd == null);
            File file = new File(wd, ".test.temp");
            assertTrue("created new file", file.createNewFile());
            assertTrue("created new file", file.delete());
        } catch (Exception e) {
            fail("Exception has occured: " + e);
        }
    }
}