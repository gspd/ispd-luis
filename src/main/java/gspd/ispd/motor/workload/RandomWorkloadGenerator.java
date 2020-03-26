package gspd.ispd.motor.workload;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.util.distribution.Distribution;
import gspd.ispd.util.distribution.DistributionBuilder;

import java.util.ArrayList;
import java.util.List;

public class RandomWorkloadGenerator extends SingleSchedulerWorkloadGenerator {

    public static final ISPDType RANDOM_WORKLOAD_TYPE = ISPDType.type(SingleSchedulerWorkloadGenerator.SINGLE_SCHEDULER_TYPE, "RANDOM_WORKLOAD_TYPE");

    //////////////////////////////////////
    /////////// CONSTRUCTOR //////////////
    //////////////////////////////////////

    public RandomWorkloadGenerator() {
        setType(RANDOM_WORKLOAD_TYPE);
    }

    ///////////////////////////////////////
    /////////// OVERRIDES /////////////////
    ///////////////////////////////////////

    @Override
    public List<Tarefa> generateTaskList() {
        List<Tarefa> taskList = new ArrayList<>();
        Distribution computationDist = DistributionBuilder.uniform(getMinComputation(), getMaxComputation()).build();
        Distribution communicationDist = DistributionBuilder.uniform(getMinCommunication(), getMaxCommunication()).build();
        int i;
        for (i = 0; i < getQuantity(); i++) {
            Tarefa task = null; // gera tarefa
            // configura tarefa utilizando as distribuições
            taskList.add(task);
        }
        return null;
    }

    @Override
    protected <G extends WorkloadGenerator> void setUpClone(G generator) {
        super.setUpClone(generator);
        RandomWorkloadGenerator randomGenerator = (RandomWorkloadGenerator) generator;
        randomGenerator.setMaxCommunication(getMaxCommunication());
        randomGenerator.setMaxComputation(getMaxComputation());
        randomGenerator.setMinCommunication(getMinCommunication());
        randomGenerator.setMinComputation(getMinComputation());
    }

    @Override
    public RandomWorkloadGenerator clone() {
        RandomWorkloadGenerator newGenerator = new RandomWorkloadGenerator();
        setUpClone(newGenerator);
        return newGenerator;
    }

    ///////////////////////////////////////
    ////////////// PROPERTIES /////////////
    ///////////////////////////////////////

    private double minComputation;
    public double getMinComputation() {
        return minComputation;
    }
    public void setMinComputation(double minComputation) {
        this.minComputation = minComputation;
    }

    private double maxComputation;
    public double getMaxComputation() {
        return maxComputation;
    }
    public void setMaxComputation(double maxComputation) {
        this.maxComputation = maxComputation;
    }

    private double minCommunication;
    public double getMinCommunication() {
        return minCommunication;
    }
    public void setMinCommunication(double minCommunication) {
        this.minCommunication = minCommunication;
    }

    private double maxCommunication;
    public double getMaxCommunication() {
        return maxCommunication;
    }
    public void setMaxCommunication(double maxCommunication) {
        this.maxCommunication = maxCommunication;
    }
}
