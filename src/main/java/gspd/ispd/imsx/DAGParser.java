package gspd.ispd.imsx;

import com.jcabi.log.Logger;
import gspd.ispd.commons.IDSystem;
import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.icons.*;
import javafx.scene.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;

public class DAGParser implements IMSXParser<DAG> {


    private Document document;
    private Map<ISPDType, IMSXParser<Icon>> iconParsers;
    private DAGParser() {
        iconParsers = new HashMap<>();
        iconParsers.put(PrecedenceIcon.PRECEDENCE_TYPE, this::parsePrecedence);
        iconParsers.put(PrefixIcon.PREFIX_TYPE, this::parsePrefix);
        iconParsers.put(FailIcon.FAIL_TYPE, this::parseFail);
        iconParsers.put(TaskIcon.TASK_TYPE, this::parseTask);
        iconParsers.put(SynchronizeIcon.SYNCHRONIZE_TYPE, this::parseSynchronize);
        iconParsers.put(ActivationIcon.ACTIVATION_TYPE, this::parseActivation);
        iconParsers.put(RecursionIcon.RECURSION_TYPE, this::parseRecursion);
        iconParsers.put(TimerIcon.TIMER_TYPE, this::parseTimer);
        iconParsers.put(SwitchIcon.SWITCH_TYPE, this::parseSwitch);
        iconParsers.put(IterativeExpansionIcon.ITEARATIVE_TYPE, this::parseIterative);
        iconParsers.put(ParallelExpansionIcon.PARALLEL_TYPE, this::parseParallel);
        iconParsers.put(RecursiveExpansionIcon.RECURSIVE_TYPE, this::parseRecursive);
    }

    private static DAGParser singleton;
    private static final Semaphore semaphore = new Semaphore(1);
    public static DAGParser getInstance(Document document) {
        try {
            if (singleton == null) {
                semaphore.acquire();
                if (singleton == null) {
                    singleton = new DAGParser();
                }
                semaphore.release();
            }
            singleton.document = document;
        } catch (InterruptedException e) {
            Logger.log(Level.SEVERE, null, "DAGParser interrupted while its creation");
        }
        return singleton;
    }


    private Element setAttrsIcon(Element element, Icon icon) {
        element.setAttribute(StringConstants.ID_ATTR, icon.getIconID());
        return element;
    }

    private Element setAttrsNodeIcon(Element element, NodeIcon icon) {
        element = setAttrsIcon(element, icon);
        element.setAttribute(StringConstants.X_ATTR, String.valueOf(icon.getCenterX()));
        element.setAttribute(StringConstants.Y_ATTR, String.valueOf(icon.getCenterY()));
        return element;
    }

    private Element setAttrsEdgeIcon(Element element, EdgeIcon icon) {
        element = setAttrsIcon(element, icon);
        element.setAttribute(StringConstants.SRC_ATTR, icon.getStartIcon().getIconID());
        element.setAttribute(StringConstants.DEST_ATTR, icon.getEndIcon().getIconID());
        element.setAttribute(StringConstants.MESSAGE_ATTR, String.valueOf(icon.getMessageSize()));
        return element;
    }

    private Element setExpansionElement(Element element, ExpansionIcon icon) {
        element = setAttrsNodeIcon(element, icon);
        element.setAttribute(StringConstants.NUMBER_ATTR, String.valueOf(icon.getNumber()));
        for (Node node : icon.getDiagram().getChildren()) {
            Element nodeElement = parseIcon((Icon) node);
            element.appendChild(nodeElement);
        }
        return element;
    }

    private Element parseTask(Icon icon) {
        TaskIcon task = (TaskIcon) icon;
        Element element = document.createElement(StringConstants.TASK_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon)icon);
        element.setAttribute(StringConstants.LABEL_ATTR, task.getLabel());
        element.setAttribute(StringConstants.LOCK_ATTR, task.getLock());
        element.setAttribute(StringConstants.COMPUTING_ATTR, String.valueOf(task.getComputingSize()));
        return element;
    }

    private Element parsePrecedence(Icon icon) {
        PrecedenceIcon precedence = (PrecedenceIcon) icon;
        Element element = document.createElement(StringConstants.PRECEDENCE_TAG);
        element = setAttrsEdgeIcon(element, precedence);
        return element;
    }

    private Element parseFail(Icon icon) {
        FailIcon fail = (FailIcon) icon;
        Element element = document.createElement(StringConstants.FAIL_TAG);
        element = setAttrsEdgeIcon(element, fail);
        return element;
    }

    private Element parsePrefix(Icon icon) {
        PrefixIcon prefix = (PrefixIcon) icon;
        Element element = document.createElement(StringConstants.PREFIX_TAG);
        element = setAttrsEdgeIcon(element, prefix);
        return element;
    }

    private Element parseSynchronize(Icon icon) {
        Element element = document.createElement(StringConstants.SYNCHRONIZE_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private Element parseRecursion(Icon icon) {
        Element element = document.createElement(StringConstants.RECURSION_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private Element parseActivation(Icon icon) {
        Element element = document.createElement(StringConstants.ACTIVATION_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private Element parseTimer(Icon icon) {
        TimerIcon timer = (TimerIcon) icon;
        Element element = document.createElement(StringConstants.TIMER_TAG);
        element = setAttrsNodeIcon(element, timer);
        element.setAttribute(StringConstants.TIME_ATTR, String.valueOf(timer.getTime()));
        return element;
    }

    private Element parseSwitch(Icon icon) {
        SwitchIcon swi = (SwitchIcon) icon;
        Element element = document.createElement(StringConstants.SWITCH_TAG);
        element = setAttrsNodeIcon(element, swi);
        for (Map.Entry<EdgeIcon, Double> entry : swi.getDistributionMap().entrySet()) {
            Element flow = document.createElement(StringConstants.FLOW_TAG);
            flow.setAttribute(StringConstants.EDGE_ATTR, entry.getKey().getIconID());
            flow.setAttribute(StringConstants.PROB_ATTR, String.valueOf(entry.getValue()));
            element.appendChild(flow);
        }
        return element;
    }

    private Element parseRecursive(Icon icon) {
        RecursiveExpansionIcon iterative = (RecursiveExpansionIcon) icon;
        Element element = document.createElement(StringConstants.RECURSIVE_TAG);
        element = setExpansionElement(element, iterative);
        return element;
    }

    private Element parseParallel(Icon icon) {
        ParallelExpansionIcon parallel = (ParallelExpansionIcon) icon;
        Element element = document.createElement(StringConstants.PARALLEL_TAG);
        element = setExpansionElement(element, parallel);
        return element;
    }

    private Element parseIterative(Icon icon) {
        IterativeExpansionIcon iterative = (IterativeExpansionIcon) icon;
        Element element = document.createElement(StringConstants.ITERATIVE_TAG);
        element = setExpansionElement(element, iterative);
        return element;
    }

    @Override
    public Element parse(DAG dag) {
        try {
            Element dagRoot = document.createElement(StringConstants.DAG_TAG);
            dagRoot.setAttribute(StringConstants.NAME_ATTR, dag.getName());
            ListIterator<Node> iterator = dag.getChildren().listIterator(dag.getChildren().size());
            while (iterator.hasPrevious()) {
                Node node = iterator.previous();
                Element nodeElement = parseIcon((Icon) node);
                dagRoot.appendChild(nodeElement);
            }
            return dagRoot;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Element parseIcon(Icon icon) {
        try {
            if (icon != null) {
                return iconParsers.get(icon.getType()).parse(icon);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document getDocument() {
        return document;
    }
}
