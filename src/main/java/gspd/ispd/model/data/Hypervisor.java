package gspd.ispd.model.data;

import java.util.List;

public class Hypervisor {

    private User owner;
    private List<User> users;

    public User getOwner() {
        return owner;
    }

    public List<User> getUsers() {
        return users;
    }
}
