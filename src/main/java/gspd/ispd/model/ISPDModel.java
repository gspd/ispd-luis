package gspd.ispd.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Properties;

public class ISPDModel {

    private Properties metadata;
    private ObservableList<User> users;
    private ObservableList<Hardware> hardware;
    private ObservableList<VM> vms;
    private Workload workload;
    private BooleanProperty saved;

    public ISPDModel() {
        metadata = new Properties();
        users = FXCollections.observableArrayList();
        hardware = FXCollections.observableArrayList();
        vms = FXCollections.observableArrayList();
        workload = new Workload();
        saved = new SimpleBooleanProperty(this, "saved", false);
    }

    public Properties getMetadata() {
        return metadata;
    }

    public void setMetadata(Properties metadata) {
        this.metadata = metadata;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public ObservableList<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(ObservableList<Hardware> hardware) {
        this.hardware = hardware;
    }

    public ObservableList<VM> getVms() {
        return vms;
    }

    public void setVms(ObservableList<VM> vms) {
        this.vms = vms;
    }

    public Workload getWorkload() {
        return workload;
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    public boolean isSaved() {
        return saved.get();
    }

    public boolean isNotSaved() {
        return !isSaved();
    }

    public BooleanProperty savedProperty() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved.set(saved);
    }

    public void saveToFile(File file) {
        // save to file routine
        saved.set(true);
    }

    public void saveToFile(String filename) {
        saveToFile(new File(filename));
    }

    public void add(User user) {
        addUser(user);
    }

    public void add(VM vm) {
        addVm(vm);
    }

    public void duplicate(VM vm) {
        int index = vms.indexOf(vm);
        duplicateVm(index);
    }

    private void addUser(User user) {
        users.add(user);
        user.setVms(vms.filtered(vm -> vm.getOwner().equals(user)));
    }

    private void addVm(VM vm) {
        vms.add(vm);
    }

    private void duplicateVm(int index) {
        VM vm = vms.get(index);
        add(vm.copy());
    }

}
