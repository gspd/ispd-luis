package gspd.ispd.imsx;

import gspd.ispd.commons.ISPDType;
import gspd.ispd.commons.StringConstants;
import gspd.ispd.motor.workload.DAGWorkloadGenerator;
import gspd.ispd.motor.workload.RandomWorkloadGenerator;
import gspd.ispd.motor.workload.SingleSchedulerWorkloadGenerator;
import gspd.ispd.motor.workload.WorkloadGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class WorkloadGeneratorParser implements IMSXParser<WorkloadGenerator> {

    private Document document;
    public WorkloadGeneratorParser(Document document) {
        this.document = document;
        initStatic();
    }

    private void initStatic() {
        if (map == null) {
            map = new HashMap<>();
            map.put(RandomWorkloadGenerator.RANDOM_WORKLOAD_TYPE, this::parseRandom);
            map.put(DAGWorkloadGenerator.DAG_WORKLOAD_TYPE, this::parseDAG);
        }
    }

    private void setUpEntry(Element element, SingleSchedulerWorkloadGenerator generator) {
        element.setAttribute(StringConstants.USER_ATTR, generator.getUser());
        element.setAttribute(StringConstants.SCHEDULER_ATTR, generator.getScheduler());
        element.setAttribute(StringConstants.QUANTITY_ATTR, String.valueOf(generator.getQuantity()));
        element.setAttribute(StringConstants.ARRIVAL_ATTR, String.valueOf(generator.getArrivalTime()));
    }

    private Element parseRandom(WorkloadGenerator generator) {
        RandomWorkloadGenerator randomGenerator = (RandomWorkloadGenerator) generator;
        Element element = document.createElement(StringConstants.RANDOM_TAG);
        setUpEntry(element, randomGenerator);
        element.setAttribute(StringConstants.MIN_COMP_ATTR, String.valueOf(randomGenerator.getMinComputation()));
        element.setAttribute(StringConstants.MAX_COMP_ATTR, String.valueOf(randomGenerator.getMaxComputation()));
        element.setAttribute(StringConstants.MIN_COMM_ATTR, String.valueOf(randomGenerator.getMinCommunication()));
        element.setAttribute(StringConstants.MAX_COMM_ATTR, String.valueOf(randomGenerator.getMaxCommunication()));
        return element;
    }

    private Element parseDAG(WorkloadGenerator generator) {
        DAGWorkloadGenerator dagGenerator = (DAGWorkloadGenerator) generator;
        Element element = document.createElement(StringConstants.DAG_TAG);
        setUpEntry(element, dagGenerator);
        element.setAttribute(StringConstants.DAG_ATTR, dagGenerator.getDag().getName());
        return element;
    }

    private static Map<ISPDType, IMSXParser<WorkloadGenerator>> map;
    @Override
    public Element parse(WorkloadGenerator data) {
        try {
            return map.get(data.getType()).parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
