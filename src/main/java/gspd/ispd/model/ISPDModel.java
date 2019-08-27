package gspd.ispd.model;

import java.util.List;

public class ISPDModel {

    public static final int GRID = 0;
    public static final int IAAS = 1;

    private Hardware hardware;
    private Workload workload;
    private List<Hypervisor> hypervisors;
    private List<User> users;

    private static int modelType;

    public Hardware getHardware() {
        return hardware;
    }

    public Workload getWorkload() {
        return workload;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Hypervisor> getHypervisors() {
        return hypervisors;
    }

    public static int getModelType() {
        return modelType;
    }
}
