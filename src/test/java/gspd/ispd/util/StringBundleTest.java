package gspd.ispd.util;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * StringBundleTest
 */
public class StringBundleTest {

    @Test
    public void testStringBundle() {
        StringBundle sb = new StringBundle("strings", new Locale("pt", "BR"));
        String str = sb.getString("string.for.test", "Luis", "Baldissera");
        assertEquals("Meu nome eh Luis e meu sobrenome Baldissera", str);
    }
}