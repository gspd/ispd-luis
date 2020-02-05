package gspd.ispd.fxgui.util;

import com.jcabi.aspects.Cacheable;
import gspd.ispd.annotations.FormField;
import gspd.ispd.annotations.FormViewer;
import javafx.scene.control.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FormBuilder {

    private Map<Class, Control> simpleFields;
    private static FormBuilder builder;

    private FormBuilder() {
        simpleFields = new HashMap<>();
        simpleFields.put(String.class, new TextField());
        simpleFields.put(Integer.class, new Spinner<Integer>());
        simpleFields.put(Double.class, new Spinner<Double>());
    }

    public static FormBuilder getInstance() {
        if (builder == null) {
            // use thread safe singleton design pattern
            synchronized (FormBuilder.class) {
                if (builder == null) {
                    builder = new FormBuilder();
                }
            }
        }
        return builder;
    }

    @Cacheable
    public FormView makeForm(Class aClass) throws IllegalArgumentException {
        FormView formView = new FormView();
        int i = 0;
        if (aClass.isAnnotationPresent(FormViewer.class)) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FormField.class)) {
                    String label = field.getAnnotation(FormField.class).value();
                    Class type = field.getType();
                    formView.addInput(label, getControl(type));
                }
            }
        } else {
            throw new IllegalArgumentException("Class " + aClass.getName() + " is not annotated with @" + FormViewer.class.getName());
        }
        return formView;
    }

    private Control getControl(Class type) {
        Control control = null;
        try {
            control = simpleFields.get(type).getClass().getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            System.out.println("Constructor not found");
        } catch (InstantiationException e) {
            System.out.println("Could not instantiate");
        } catch (IllegalAccessException e) {
            System.out.println("Not allowed");
        } catch (InvocationTargetException e) {
            System.out.println("Invocation error");
        }
        return control;
    }
}
