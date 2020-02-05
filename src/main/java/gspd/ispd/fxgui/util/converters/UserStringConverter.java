package gspd.ispd.fxgui.util.converters;

import gspd.ispd.model.User;
import javafx.util.StringConverter;

import java.util.regex.Pattern;

public class UserStringConverter extends StringConverter<User> {

    @Override
    public String toString(User object) {
        return "user(" + object.getName() + ")";
    }

    @Override
    public User fromString(String string) {
        return null;
    }
}
