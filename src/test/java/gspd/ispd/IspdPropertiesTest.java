package gspd.ispd;

import static org.junit.Assert.*;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Locale;


/**
 * IspdPropertiesTest
 */
public class IspdPropertiesTest {
    
    @Test
    public void testLocale() {
        IspdProperties.load();
        Locale loc = IspdProperties.getLocale();
        assertFalse("locale is not NULL", loc == null);
        assertEquals("locale is rigth", loc, new Locale("en","US"));
    }

    @Test
    public void testWorkingDirectory() {
        IspdProperties.load();
        String wd = IspdProperties.getWorkingDirectory();
        assertFalse("working directory is not NULL", wd == null);
        assertEquals("working directory is right", Paths.get(wd), Paths.get("/home/luis/IspdProjects"));
    }
}