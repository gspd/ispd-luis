package gspd.ispd;

import com.sun.tools.javac.Main;
import gspd.ispd.game.ISPDGame;
import gspd.ispd.model.ISPDModel;
import javafx.application.Application;

import java.util.*;

public class ISPD {

    /**
     * This resource bundle loads all the strings for iSPD
     */
    public static final ResourceBundle strings = ResourceBundle.getBundle("gspd.ispd.strings", new Locale("pt", "BR"));

    public static void main(String[] args) {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        if (argsList.contains("--javafx")) {
            argsList.remove("--javafx");
            args = new String[argsList.size()];
            args = argsList.toArray(args);
            Application.launch(GUI.class, args);
        } else if (argsList.contains("--game")) {
            argsList.remove("--game");
            args = new String[argsList.size()];
            args = argsList.toArray(args);
            Application.launch(ISPDGame.class, args);
        } else {
            if (argsList.contains("--swing")) {
                argsList.remove("--swing");
                args = new String[argsList.size()];
                args = argsList.toArray(args);
            }
            MainSwing.main(args);
        }
    }
}
