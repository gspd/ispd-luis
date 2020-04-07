package gspd.ispd.util.workload;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.workload.GenerateWorkloadEntry;
import gspd.ispd.fxgui.workload.TraceOrRandomPane;
import gspd.ispd.fxgui.workload.WorkloadPane;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.motor.workload.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class converts the workload defined by the
 * user in the workload table to the WorkloadGenerator
 * structure defined in the motor package, and vice-versa.
 */
public class WorkloadConverter {

    public static WorkloadGenerator toGenerator(WorkloadPane pane) {
        WorkloadGenerator workload = null;
        if (pane.getTraceOrRandomPane().getChoice() == TraceOrRandomPane.TRACE) { // Trace Workload
            // ...
        } else if (pane.getTraceOrRandomPane().getChoice() == TraceOrRandomPane.GENERATE) { // Generate Workload
            workload = new ForSchedulerWorkloadGenerator();
            ForSchedulerWorkloadGenerator forWorkload = (ForSchedulerWorkloadGenerator) workload;
            List<GenerateWorkloadEntry> entries = pane.getGeneratePane().getEntries();
            for (GenerateWorkloadEntry entry : entries) {
                SingleSchedulerWorkloadGenerator sw = null;
                if (entry.getType().equals(StringConstants.RANDOM_TYPE)) {
                    sw = new RandomWorkloadGenerator();
                    RandomWorkloadGenerator randomW = (RandomWorkloadGenerator) sw;
                    Scanner scanner = new Scanner(entry.getData());
                    randomW.setMinComputation(scanner.nextDouble());
                    randomW.setMaxComputation(scanner.nextDouble());
                    randomW.setMinCommunication(scanner.nextDouble());
                    randomW.setMaxCommunication(scanner.nextDouble());
                } else if (entry.getType().equals(StringConstants.DAG_TYPE)) {
                    sw = new DAGWorkloadGenerator();
                    DAGWorkloadGenerator dagW = (DAGWorkloadGenerator) sw;
                    DAG dag = DAGContainer.getInstance().get(entry.getData());
                    if (dag != null) {
                        dagW.setDag(dag);
                    }
                }
                if (sw != null) {
                    sw.setUser(entry.getUser());
                    sw.setScheduler(entry.getScheduler());
                    sw.setQuantity(entry.getQuantity());
                    sw.setArrivalTime(entry.getArrivalTime());
                    forWorkload.addGenerator(sw);
                }
            }
        }
        return workload;
    }

    public static void setupPane(WorkloadPane pane, WorkloadGenerator workload) {
        if (workload != null) {
            if (workload.getType().isTypeOf(TraceWorkloadGenerator.TRACE_WORKLOAD_TYPE)) {
                pane.getTraceOrRandomPane().setChoice(TraceOrRandomPane.TRACE);
                // ...
            } else if (workload.getType().isTypeOf(ForSchedulerWorkloadGenerator.FOR_SCHEDULER_TYPE)) {
                ForSchedulerWorkloadGenerator forWorkload = (ForSchedulerWorkloadGenerator) workload;
                pane.getTraceOrRandomPane().setChoice(TraceOrRandomPane.GENERATE);
                List<GenerateWorkloadEntry> entries = new ArrayList<>();
                for (WorkloadGenerator gen : forWorkload.getGenerators()) {
                    SingleSchedulerWorkloadGenerator singleGen = (SingleSchedulerWorkloadGenerator) gen;
                    GenerateWorkloadEntry entry = new GenerateWorkloadEntry();
                    if (singleGen.getType().isTypeOf(RandomWorkloadGenerator.RANDOM_WORKLOAD_TYPE)) {
                        RandomWorkloadGenerator randomGen = (RandomWorkloadGenerator) singleGen;
                        entry.setType(StringConstants.RANDOM_TYPE);
                        entry.setData(String.join(" ",
                                String.valueOf(randomGen.getMinComputation()),
                                String.valueOf(randomGen.getMaxComputation()),
                                String.valueOf(randomGen.getMinCommunication()),
                                String.valueOf(randomGen.getMaxCommunication())
                        ));
                    } else if (singleGen.getType().isTypeOf(DAGWorkloadGenerator.DAG_WORKLOAD_TYPE)) {
                        DAGWorkloadGenerator dagGen = (DAGWorkloadGenerator) singleGen;
                        entry.setType(StringConstants.DAG_TYPE);
                        entry.setData(dagGen.getDag().getName());
                    }
                    entry.setUser(singleGen.getUser());
                    entry.setScheduler(singleGen.getScheduler());
                    entry.setQuantity(singleGen.getQuantity());
                    entry.setArrivalTime(singleGen.getArrivalTime());
                    entries.add(entry);
                }
                pane.getGeneratePane().setEntries(entries);
                Platform.runLater(pane::loadGeneratePane);
            }
        }
    }
}
