package gspd.ispd.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MiniInfo {
    /**
     * The mini info identity of a given property
     * @return
     */
    String value();
}
