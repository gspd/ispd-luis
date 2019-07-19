package gspd.ispd.util;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Locale;

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

    @Test
    public void testResolveString() {
        StringBundle sb = new StringBundle("strings", new Locale("pt", "BR"));
        String str = sb.resolveString("2^10 = ${string.for.test}");
        assertEquals("2^10 = 1024", str);
    }
}