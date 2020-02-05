package gspd.ispd.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormField {
    String value();
}
