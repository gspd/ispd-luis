package gspd.ispd.commons;

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
    public void testRevolveString() {
        StringBundle sb = new StringBundle("strings", new Locale("pt", "BR"));
        String str = sb.resolveString("This is ${ispd.name.short}");
        assertEquals("This is iSPD", str);
    }
}