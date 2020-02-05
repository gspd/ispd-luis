package gspd.ispd.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Help {
    /**
     * The URL of file with help text
     * @return the URL of file with help text
     */
    String value();
}
