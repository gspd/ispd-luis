package gspd.ispd.fxgui.custom;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import org.controlsfx.control.textfield.CustomTextField;

public class ValidatedTextField extends CustomTextField {

    BooleanProperty valid = new SimpleBooleanProperty(this, "valid", false);

    public ValidatedTextField() {
        ValidationIcon icon = new ValidationIcon();
        setRight(icon);
    }

    public boolean isValid() {
        return valid.get();
    }

    public ReadOnlyBooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }

    private static class ValidationIcon extends ImageView {

        private BooleanProperty valid = new SimpleBooleanProperty();

    }

}
