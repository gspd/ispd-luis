package gspd.ispd.imsx;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.icons.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAGLoader implements IMSXLoader<DAG> {

    private Map<String, Icon> idTransform;
    private Map<String, IMSXLoader<? extends Icon>> iconLoaders;
    private DAGLoader() {
        idTransform = new HashMap<>();
        iconLoaders = new HashMap<>();
        iconLoaders.put(StringConstants.TASK_TAG, this::loadTask);
        iconLoaders.put(StringConstants.TIMER_TAG, this::loadTimer);
        iconLoaders.put(StringConstants.SWITCH_TAG, this::loadSwitch);
        iconLoaders.put(StringConstants.ACTIVATION_TAG, this::loadActivation);
        iconLoaders.put(StringConstants.SYNCHRONIZE_TAG, this::loadSynchronize);
        iconLoaders.put(StringConstants.RECURSION_TAG, this::loadRecursion);
        iconLoaders.put(StringConstants.PRECEDENCE_TAG, this::loadPrecedence);
        iconLoaders.put(StringConstants.PREFIX_TAG, this::loadPrefix);
        iconLoaders.put(StringConstants.FAIL_TAG, this::loadFail);
        iconLoaders.put(StringConstants.ITERATIVE_TAG, this::loadIterative);
        iconLoaders.put(StringConstants.PARALLEL_TAG, this::loadParallel);
        iconLoaders.put(StringConstants.RECURSIVE_TAG, this::loadRecursive);
    }

    private static DAGLoader singleton;
    private static final Semaphore semaphore = new Semaphore(1);
    public static DAGLoader getInstance() {
        if (singleton == null) {
            try {
                semaphore.acquire();
                if (singleton == null) {
                    singleton = new DAGLoader();
                }
                semaphore.release();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, "Error while creating DAGLoader: " + e);
            }
        }
        return singleton;
    }

    private void setupIcon(Element element, Icon icon) {
        String id = element.getAttribute(StringConstants.ID_ATTR);
        idTransform.put(id, icon);
    }

    private void setupNodeIcon(Element element, NodeIcon icon) {
        setupIcon(element, icon);
        icon.setCenterX(Double.parseDouble(element.getAttribute(StringConstants.X_ATTR)));
        icon.setCenterY(Double.parseDouble(element.getAttribute(StringConstants.Y_ATTR)));
    }

    private void setupEdgeIcon(Element element, EdgeIcon icon) {
        setupIcon(element, icon);
        icon.setStartIcon((NodeIcon) idTransform.get(element.getAttribute(StringConstants.SRC_ATTR)));
        icon.setEndIcon((NodeIcon) idTransform.get(element.getAttribute(StringConstants.DEST_ATTR)));
        icon.setMessageSize(Double.parseDouble(element.getAttribute(StringConstants.MESSAGE_ATTR)));
    }

    private void setupExpansionIcon(Element element, ExpansionIcon icon) throws IMSXLoadException {
        setupNodeIcon(element, icon);
        DAG dag = createDAGFrom(element);
        icon.setDiagram(dag);
    }

    private TaskIcon loadTask(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.TASK_TAG)) {
            throw new IMSXLoadException();
        }
        TaskIcon icon = new TaskIcon();
        setupNodeIcon(element, icon);
        icon.setComputingSize(Double.parseDouble(element.getAttribute(StringConstants.COMPUTING_ATTR)));
        icon.setLabel(element.getAttribute(StringConstants.LABEL_ATTR));
        String lock = element.getAttribute(StringConstants.LOCK_ATTR);
        icon.setLock(lock.equals("") ? null : lock);
        return icon;
    }

    private TimerIcon loadTimer(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.TIMER_TAG)) {
            throw new IMSXLoadException();
        }
        TimerIcon icon = new TimerIcon();
        setupNodeIcon(element, icon);
        icon.setTime(Double.parseDouble(element.getAttribute(StringConstants.TIME_ATTR)));
        return icon;
    }

    private SwitchIcon loadSwitch(Element element) throws  IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.SWITCH_TAG)) {
            throw new IMSXLoadException();
        }
        SwitchIcon icon = new SwitchIcon();
        setupNodeIcon(element, icon);
        return icon;
    }

    private RecursionIcon loadRecursion(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.RECURSION_TAG)) {
            throw new IMSXLoadException();
        }
        RecursionIcon icon = new RecursionIcon();
        setupNodeIcon(element, icon);
        return icon;
    }

    private ActivationIcon loadActivation(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.ACTIVATION_TAG)) {
            throw new IMSXLoadException();
        }
        ActivationIcon icon = new ActivationIcon();
        setupNodeIcon(element, icon);
        return icon;
    }

    private SynchronizeIcon loadSynchronize(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.SYNCHRONIZE_TAG)) {
            throw new IMSXLoadException();
        }
        SynchronizeIcon icon = new SynchronizeIcon();
        setupNodeIcon(element, icon);
        return icon;
    }

    private PrecedenceIcon loadPrecedence(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.PRECEDENCE_TAG)) {
            throw new IMSXLoadException();
        }
        PrecedenceIcon icon = new PrecedenceIcon();
        setupEdgeIcon(element, icon);
        return icon;
    }

    private PrefixIcon loadPrefix(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.PREFIX_TAG)) {
            throw new IMSXLoadException();
        }
        PrefixIcon icon = new PrefixIcon();
        setupEdgeIcon(element, icon);
        return icon;
    }

    private FailIcon loadFail(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.FAIL_TAG)) {
            throw new IMSXLoadException();
        }
        FailIcon icon = new FailIcon();
        setupEdgeIcon(element, icon);
        return icon;
    }

    private IterativeExpansionIcon loadIterative(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.ITERATIVE_TAG)) {
            throw new IMSXLoadException();
        }
        IterativeExpansionIcon icon = new IterativeExpansionIcon();
        setupExpansionIcon(element, icon);
        return icon;
    }

    private ParallelExpansionIcon loadParallel(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.PARALLEL_TAG)) {
            throw new IMSXLoadException();
        }
        ParallelExpansionIcon icon = new ParallelExpansionIcon();
        setupExpansionIcon(element, icon);
        return icon;
    }

    private RecursiveExpansionIcon loadRecursive(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.RECURSIVE_TAG)) {
            throw new IMSXLoadException();
        }
        RecursiveExpansionIcon icon = new RecursiveExpansionIcon();
        setupExpansionIcon(element, icon);
        return icon;
    }

    @Override
    public DAG load(Element element) throws IMSXLoadException {
        if (!element.getTagName().equals(StringConstants.DAG_TAG)) {
            throw new IMSXLoadException();
        }
        return createDAGFrom(element);
    }

    private DAG createDAGFrom(Element element) throws IMSXLoadException {
        DAG dag = new DAG();
        element.normalize();
        if (element.getTagName().equals(StringConstants.DAG_TAG)) {
            dag.setName(element.getAttribute(StringConstants.NAME_ATTR));
        }
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                Icon icon = loadIcon(e);
                dag.add(icon);
            }
        }
        return dag;
    }

    private Icon loadIcon(Element element) throws IMSXLoadException {
        return iconLoaders.get(element.getTagName()).load(element);
    }
}
