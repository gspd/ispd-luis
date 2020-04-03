package gspd.ispd.imsx;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.motor.workload.*;
import gspd.ispd.util.workload.DAGContainer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkloadLoader implements IMSXLoader<WorkloadGenerator> {

    private Map<String, IMSXLoader<WorkloadGenerator>> loaders;
    private WorkloadLoader() {
        loaders = new HashMap<>();
        loaders.put(StringConstants.TRACE_LOAD_TAG, this::loadTrace);
        loaders.put(StringConstants.FOR_SCHEDULER_TAG, this::loadForScheduler);
        loaders.put(StringConstants.DAG_LOAD_TAG, this::loadDAG);
        loaders.put(StringConstants.RANDOM_LOAD_TAG, this::loadRandom);
    }

    private static WorkloadLoader singleton;
    private static final Semaphore semaphore = new Semaphore(1);
    public static WorkloadLoader getInstance() {
        if (singleton == null) {
            try {
                semaphore.acquire();
                if (singleton == null) {
                    singleton = new WorkloadLoader();
                }
                semaphore.release();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "Interrupted while creating Workload Loader: " + e);
            }
        }
        return singleton;
    }

    private WorkloadGenerator loadForScheduler(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.FOR_SCHEDULER_TAG)) {
            throw new IMSXLoadException();
        }
        ForSchedulerWorkloadGenerator generator = new ForSchedulerWorkloadGenerator();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                generator.addGenerator((SingleSchedulerWorkloadGenerator) load(e));
            }
        }
        return generator;
    }

    private void setupSingleWorkload(Element element, SingleSchedulerWorkloadGenerator generator) {
        generator.setUser(element.getAttribute(StringConstants.USER_ATTR));
        generator.setScheduler(element.getAttribute(StringConstants.SCHEDULER_ATTR));
        generator.setQuantity(Integer.parseInt(element.getAttribute(StringConstants.QUANTITY_ATTR)));
        generator.setArrivalTime(Double.parseDouble(element.getAttribute(StringConstants.ARRIVAL_ATTR)));
    }

    private WorkloadGenerator loadRandom(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.RANDOM_LOAD_TAG)) {
            throw new IMSXLoadException();
        }
        RandomWorkloadGenerator generator = new RandomWorkloadGenerator();
        setupSingleWorkload(element, generator);
        generator.setMinComputation(Double.parseDouble(element.getAttribute(StringConstants.MIN_COMP_ATTR)));
        generator.setMaxComputation(Double.parseDouble(element.getAttribute(StringConstants.MAX_COMP_ATTR)));
        generator.setMinCommunication(Double.parseDouble(element.getAttribute(StringConstants.MIN_COMM_ATTR)));
        generator.setMaxCommunication(Double.parseDouble(element.getAttribute(StringConstants.MAX_COMM_ATTR)));
        return generator;
    }

    private WorkloadGenerator loadDAG(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.DAG_LOAD_TAG)) {
            throw new IMSXLoadException();
        }
        DAGWorkloadGenerator generator = new DAGWorkloadGenerator();
        setupSingleWorkload(element, generator);
        generator.setDag(DAGContainer.getInstance().get(element.getAttribute(StringConstants.DAG_ATTR)));
        return generator;
    }

    private WorkloadGenerator loadTrace(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.TRACE_LOAD_TAG)) {
            throw new IMSXLoadException();
        }
        return null;
    }

    @Override
    public WorkloadGenerator load(Element element) throws IMSXLoadException {
        return loaders.get(element.getTagName()).load(element);
    }
}
