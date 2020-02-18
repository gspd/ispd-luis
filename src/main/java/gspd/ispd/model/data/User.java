package gspd.ispd.model.data;

import java.util.List;

public class User {

    private static int ID = 1;

    private String name;
    private int id;
    private List<Machine> machines;
    private List<Link> links;
    private List<VM> vms;
    private List<Job> jobs;

    private boolean empty;

    public User() {
        this(null);
    }

    public User(String name) {
        setName(name);
        empty = true;
        id = ID++;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<VM> getVms() {
        return vms;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public int getId() {
        return id;
    }
}
