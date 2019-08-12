package gspd.ispd.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.Test;

import gspd.ispd.Main;

/**
 * StringPropertiesTest
 */
public class StringPropertiesTest {

    @Test
    public void testStringProperties() {
        try {
            Properties prop = new Properties();
            prop.load(Main.class.getResourceAsStream("/settings.properties"));
            StringPropertiesBind spb = new StringPropertiesBind(prop);
            String str = spb.resolveString("The motor has ${test.setting} bytes in the buffer");
            assertEquals("The motor has 1024 bytes in the buffer", str);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}