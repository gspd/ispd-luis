package gspd.ispd.fxgui.workload;

import gspd.ispd.commons.StringConstants;

public class GenerateWorkloadEntry {

    public GenerateWorkloadEntry() {
        setUser("");
        setData("0.0 0.0 0.0 0.0");
        setType(RANDOM_TYPE);
        setArrivalTime(0.0);
        setQuantity(1);
        setScheduler("");
    }

    ///////////////////////////////
    ///////////// METHODS /////////
    ///////////////////////////////

    @Override
    protected GenerateWorkloadEntry clone() {
        GenerateWorkloadEntry entry = new GenerateWorkloadEntry();
        entry.setUser(getUser());
        entry.setScheduler(getScheduler());
        entry.setQuantity(getQuantity());
        entry.setArrivalTime(getArrivalTime());
        entry.setType(getType());
        entry.setData(getData());
        return entry;
    }

    ///////////////////////////
    //////// PROPERTIES ///////
    ///////////////////////////

    /**
     * The user
     */
    public static final String USER_PROPERTY = "user";
    private String user;
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * The scheduler
     */
    public static final String SCHEDULER_PROPERTY = "scheduler";
    private String scheduler;
    public String getScheduler() {
        return scheduler;
    }
    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * The quantity of tasks
     */
    public static final String QUANTITY_PROPERTY = "quantity";
    private int quantity;
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * The average time of arrival
     */
    public static final String ARRIVAL_TIME_PROPERTY = "arrivalTime";
    private double arrivalTime;
    public double getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * The type of the workload
     */
    public static final String TYPE_PROPERTY = "type";
    public static final String DAG_TYPE = "DAG";
    public static final String RANDOM_TYPE = "Random";
    public String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The data
     */
    public static final String DATA_PROPERTY = "data";
    private String data;
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
