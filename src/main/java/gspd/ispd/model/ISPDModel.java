package gspd.ispd.model;

import gspd.ispd.annotations.IMSX;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ISPDModel {

    private Properties metadata;
    private List<User> users;
    private List<Hardware> hardware;
    private List<VM> vms;
    private Workload workload;

    private boolean saved;

    public ISPDModel() {
        metadata = new Properties();
        users = new ArrayList<>();
        hardware = new ArrayList<>();
        vms = new ArrayList<>();
        workload = new Workload();
        saved = false;
    }

    public Properties getMetadata() {
        return metadata;
    }

    public void setMetadata(Properties metadata) {
        this.metadata = metadata;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    public List<VM> getVms() {
        return vms;
    }

    public void setVms(List<VM> vms) {
        this.vms = vms;
    }

    public Workload getWorkload() {
        return workload;
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isNotSaved() {
        return ! isSaved();
    }

    public void saveToFile(File file) {
        // save to file routine
        saved = true;
    }

    public void saveToFile(String filename) {
        saveToFile(new File(filename));
    }
}
