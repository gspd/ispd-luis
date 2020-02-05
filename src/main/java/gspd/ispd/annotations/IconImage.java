package gspd.ispd.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IconImage {
    String value();
}
