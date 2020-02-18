package gspd.ispd.fxgui.util.converters;

import gspd.ispd.model.data.User;
import javafx.util.StringConverter;

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
