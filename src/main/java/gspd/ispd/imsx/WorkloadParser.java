package gspd.ispd.imsx;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.commons.StringConstants;
import gspd.ispd.motor.workload.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkloadParser implements IMSXParser<WorkloadGenerator> {

    private Document document;
    private static Map<ISPDType, IMSXParser<WorkloadGenerator>> workloadParsers;
    private WorkloadParser() {
        workloadParsers = new HashMap<>();
        workloadParsers.put(RandomWorkloadGenerator.RANDOM_WORKLOAD_TYPE, this::parseRandom);
        workloadParsers.put(DAGWorkloadGenerator.DAG_WORKLOAD_TYPE, this::parseDAG);
        workloadParsers.put(ForSchedulerWorkloadGenerator.FOR_SCHEDULER_TYPE, this::parseForScheduler);
    }

    private static WorkloadParser singleton;
    private static final Semaphore semaphore = new Semaphore(1);
    public static WorkloadParser getInstance(Document document) {
        if (singleton == null) {
            try {
                semaphore.acquire();
                if (singleton == null) {
                    singleton = new WorkloadParser();
                }
                semaphore.release();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "Fail while creating WorkloadGeneratorParser: " + e);
            }
        }
        singleton.document = document;
        return singleton;
    }

    private void setUpEntry(Element element, SingleSchedulerWorkloadGenerator generator) {
        element.setAttribute(StringConstants.USER_ATTR, generator.getUser());
        element.setAttribute(StringConstants.SCHEDULER_ATTR, generator.getScheduler());
        element.setAttribute(StringConstants.QUANTITY_ATTR, String.valueOf(generator.getQuantity()));
        element.setAttribute(StringConstants.ARRIVAL_ATTR, String.valueOf(generator.getArrivalTime()));
    }

    private Element parseRandom(WorkloadGenerator generator) {
        RandomWorkloadGenerator randomGenerator = (RandomWorkloadGenerator) generator;
        Element element = document.createElement(StringConstants.RANDOM_LOAD_TAG);
        setUpEntry(element, randomGenerator);
        element.setAttribute(StringConstants.MIN_COMP_ATTR, String.valueOf(randomGenerator.getMinComputation()));
        element.setAttribute(StringConstants.MAX_COMP_ATTR, String.valueOf(randomGenerator.getMaxComputation()));
        element.setAttribute(StringConstants.MIN_COMM_ATTR, String.valueOf(randomGenerator.getMinCommunication()));
        element.setAttribute(StringConstants.MAX_COMM_ATTR, String.valueOf(randomGenerator.getMaxCommunication()));
        return element;
    }

    private Element parseDAG(WorkloadGenerator generator) {
        DAGWorkloadGenerator dagGenerator = (DAGWorkloadGenerator) generator;
        Element element = document.createElement(StringConstants.DAG_LOAD_TAG);
        setUpEntry(element, dagGenerator);
        element.setAttribute(StringConstants.DAG_ATTR, dagGenerator.getDag().getName());
        return element;
    }

    private Element parseForScheduler(WorkloadGenerator generator) {
        ForSchedulerWorkloadGenerator forGenerator = (ForSchedulerWorkloadGenerator) generator;
        Element element = document.createElement(StringConstants.FOR_SCHEDULER_TAG);
        for (WorkloadGenerator wg : forGenerator.getGenerators()) {
            element.appendChild(parse(wg));
        }
        return element;
    }

    @Override
    public Element parse(WorkloadGenerator data) {
        try {
            return workloadParsers.get(data.getType()).parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
