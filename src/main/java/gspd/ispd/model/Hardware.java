package gspd.ispd.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Hardware {

    private ObjectProperty<User> owner;

    public Hardware() {
        this.owner = new SimpleObjectProperty<>(this, "owner", null);
    }

    public User getOwner() {
        return owner.get();
    }

    public ObjectProperty<User> ownerProperty() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner.set(owner);
    }

}
