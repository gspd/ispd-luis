package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;

public abstract class SingleSchedulerWorkloadGenerator extends WorkloadGenerator {

    public static final ISPDType SINGLE_SCHEDULER_TYPE = ISPDType.type(WorkloadGenerator.WORKLOAD_TYPE, "SINGLE_SCHEDULER_TYPE");

    /////////////////////////////
    //////// CONSTRUCTOR ////////
    /////////////////////////////

    public SingleSchedulerWorkloadGenerator() {
        setType(SINGLE_SCHEDULER_TYPE);
    }

    ////////////////////////////
    ///////// OVERRIDES ////////
    ////////////////////////////

    @Override
    protected <G extends WorkloadGenerator> void setUpClone(G generator) {
        super.setUpClone(generator);
        SingleSchedulerWorkloadGenerator singleGenerator = (SingleSchedulerWorkloadGenerator) generator;
        singleGenerator.setUser(getUser());
        singleGenerator.setScheduler(getScheduler());
        singleGenerator.setQuantity(getQuantity());
        singleGenerator.setArrivalTime(getArrivalTime());
    }

    /////////////////////////////
    ////////// ABSTRACTS ////////
    /////////////////////////////

    // just to make sure it is public and not protected
    // as the default JDK clone
    @Override
    public abstract SingleSchedulerWorkloadGenerator clone();

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

}
