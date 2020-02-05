package gspd.ispd.annotations;


import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormViewer {
    String value();
}
