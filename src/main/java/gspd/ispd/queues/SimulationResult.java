package gspd.ispd.queues;

public abstract class SimulationResult<E> {

    private String title;
    private E value;
    private E error;
    private Simulation simulation;

    public SimulationResult(Simulation simulation, String title) {
        this.simulation = simulation;
        this.title = title;
    }

    public E getValue() {
        return value;
    }

    public E getError() {
        return error;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    @Override
    public String toString() {
        return title + "=" + value + ((error != null) ? " error(" + error + ")" : "");
    }

    public abstract void submit(E newResult);
}
