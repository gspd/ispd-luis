package gspd.ispd.queues;

import java.util.HashMap;
import java.util.Map;

public class SimulationResults {
    private static final Map<Simulation, SimulationResults> results = new HashMap<>();
    // Overall simulation metrics
    private SimulationResult<Double> simulationTime;
    private SimulationResult<Double> simulatedTime;
    // Overall service centres metrics
    private SimulationResult<Double> averageSystemUtilization;
    private SimulationResult<Double> averageContiguousUtilization;
    // Overall client metrics
    private SimulationResult<Double> averageQueueTime;
    private SimulationResult<Double> averageServiceTime;
    private SimulationResult<Double> averageQueueSize;

    /**
     * The simulation this simulation results watches
     */
    private Simulation simulation;

    private SimulationResults(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public static SimulationResults getInstance(Simulation simulation) {
        if (results.get(simulation) == null) {
            synchronized (SimulationResults.class) {
                if (results.get(simulation) == null) {
                    results.put(simulation, new SimulationResults(simulation));
                }
            }
        }
        return results.get(simulation);
    }
}
