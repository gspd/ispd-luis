package gspd.ispd;

import com.sun.tools.javac.Main;
import javafx.application.Application;

import java.util.*;

public class ISPD {

    public static final ResourceBundle strings = ResourceBundle.getBundle("strings", new Locale("en", "US"));

    public static void main(String[] args) {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        if (argsList.contains("--swing")) {
            argsList.remove("--swing");
            args = new String[argsList.size()];
            args = argsList.toArray(args);
            MainSwing.main(args);
        } else {
            Application.launch(GUI.class, args);
        }
    }
}
