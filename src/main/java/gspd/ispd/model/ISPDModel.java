package gspd.ispd.model;

import gspd.ispd.model.data.User;

import java.util.List;
import java.util.Properties;

public class ISPDModel {

    private Properties metadata;
    private List<User> users;

    public ISPDModel() {
        metadata = new Properties();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Properties getMetadata() {
        return metadata;
    }

    public void setMetadata(Properties metadata) {
        this.metadata = metadata;
    }
}
