package gspd.ispd.imsx;

import gspd.ispd.fxgui.commons.EdgeIcon;
import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.commons.ISPDType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.icons.*;
import javafx.scene.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DAGParser implements IMSXParser<DAG> {


    private Document document;
    public DAGParser(Document document) {
        try {
            this.document = document;
            initStatic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<ISPDType, IMSXParser<Icon>> map;
    private void initStatic() {
        if (map == null) {
            map = new HashMap<>();
            map.put(PrecedenceIcon.PRECEDENCE_TYPE, this::parsePrecedence);
            map.put(PrefixIcon.PREFIX_TYPE, this::parsePrefix);
            map.put(FailIcon.FAIL_TYPE, this::parseFail);
            map.put(TaskIcon.TASK_TYPE, this::parseTask);
            map.put(SynchronizeIcon.SYNCHRONIZE_TYPE, this::parseSynchronize);
            map.put(ActivationIcon.ACTIVATION_TYPE, this::parseActivation);
            map.put(RecursionIcon.RECURSION_TYPE, this::parseRecursion);
            map.put(TimerIcon.TIMER_TYPE, this::parseTimer);
            map.put(SwitchIcon.SWITCH_TYPE, this::parseSwitch);
            map.put(IterativeExpansionIcon.ITEARATIVE_TYPE, this::parseIterative);
            map.put(ParallelExpansionIcon.PARALLEL_TYPE, this::parseParallel);
            map.put(RecursiveExpansionIcon.RECURSIVE_TYPE, this::parseRecursive);
        }
    }

    private static final String ID_ATTR = "id";
    private Element setAttrsIcon(Element element, Icon icon) {
        element.setAttribute(ID_ATTR, icon.getIconID());
        return element;
    }

    private static final String X_ATTR = "x";
    private static final String Y_ATTR = "y";
    private Element setAttrsNodeIcon(Element element, NodeIcon icon) {
        element = setAttrsIcon(element, icon);
        element.setAttribute(X_ATTR, String.valueOf(icon.getCenterX()));
        element.setAttribute(Y_ATTR, String.valueOf(icon.getCenterY()));
        return element;
    }

    private static final String START_ATTR = "src";
    private static final String END_ATTR = "dest";
    private Element setAttrsEdgeIcon(Element element, EdgeIcon icon) {
        element = setAttrsIcon(element, icon);
        element.setAttribute(START_ATTR, icon.getStartIcon().getIconID());
        element.setAttribute(END_ATTR, icon.getEndIcon().getIconID());
        return element;
    }

    private static final String NUMBER_ATTR = "number";
    private Element setExpansionElement(Element element, ExpansionIcon icon) {
        element = setAttrsNodeIcon(element, icon);
        element.setAttribute(NUMBER_ATTR, String.valueOf(icon.getNumber()));
        for (Node node : icon.getDiagram().getChildren()) {
            Element nodeElement = parseIcon((Icon) node);
            element.appendChild(nodeElement);
        }
        return element;
    }

    private static final String TASK_TAG = "task";
    private static final String LABEL_ATTR = "label";
    private static final String LOCK_ATTR = "lock";
    private static final String COMPUTING_ATTR = "computing";
    private Element parseTask(Icon icon) {
        TaskIcon task = (TaskIcon) icon;
        Element element = document.createElement(TASK_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon)icon);
        element.setAttribute(LABEL_ATTR, task.getLabel());
        element.setAttribute(LOCK_ATTR, task.getLabel());
        element.setAttribute(COMPUTING_ATTR, String.valueOf(task.getComputingSize()));
        return element;
    }

    private static final String PRECEDENCE_TAG = "precedence";
    private Element parsePrecedence(Icon icon) {
        PrecedenceIcon precedence = (PrecedenceIcon) icon;
        Element element = document.createElement(PRECEDENCE_TAG);
        element = setAttrsEdgeIcon(element, precedence);
        return element;
    }

    private static final String FAIL_TAG = "fail";
    private Element parseFail(Icon icon) {
        FailIcon fail = (FailIcon) icon;
        Element element = document.createElement(FAIL_TAG);
        element = setAttrsEdgeIcon(element, fail);
        return element;
    }

    private static final String PREFIX_TAG = "prefix";
    private Element parsePrefix(Icon icon) {
        PrefixIcon prefix = (PrefixIcon) icon;
        Element element = document.createElement(PREFIX_TAG);
        element = setAttrsEdgeIcon(element, prefix);
        return element;
    }

    private static final String SYNC_TAG = "sync";
    private Element parseSynchronize(Icon icon) {
        Element element = document.createElement(SYNC_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private static final String RECURSION_TAG = "recur";
    private Element parseRecursion(Icon icon) {
        Element element = document.createElement(RECURSION_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private static final String ACTIVATION_TAG = "act";
    private Element parseActivation(Icon icon) {
        Element element = document.createElement(ACTIVATION_TAG);
        element = setAttrsNodeIcon(element, (NodeIcon) icon);
        return element;
    }

    private static final String TIMER_TAG = "timer";
    private static final String TIME_ATTR = "time";
    private Element parseTimer(Icon icon) {
        TimerIcon timer = (TimerIcon) icon;
        Element element = document.createElement(TIMER_TAG);
        element = setAttrsNodeIcon(element, timer);
        element.setAttribute(TIME_ATTR, String.valueOf(timer.getTime()));
        return element;
    }

    private static final String SWITCH_TAG = "switch";
    private Element parseSwitch(Icon icon) {
        SwitchIcon swi = (SwitchIcon) icon;
        Element element = document.createElement(SWITCH_TAG);
        element = setAttrsNodeIcon(element, swi);
        return element;
    }

    private static final String RECURSIVE_TAG = "recursive";
    private Element parseRecursive(Icon icon) {
        RecursiveExpansionIcon iterative = (RecursiveExpansionIcon) icon;
        Element element = document.createElement(RECURSIVE_TAG);
        element = setExpansionElement(element, iterative);
        return element;
    }

    private static final String PARALLEL_TAG = "parallel";
    private Element parseParallel(Icon icon) {
        ParallelExpansionIcon parallel = (ParallelExpansionIcon) icon;
        Element element = document.createElement(PARALLEL_TAG);
        element = setExpansionElement(element, parallel);
        return element;
    }

    private static final String ITERATIVE_TAG = "iterative";
    private Element parseIterative(Icon icon) {
        IterativeExpansionIcon iterative = (IterativeExpansionIcon) icon;
        Element element = document.createElement(ITERATIVE_TAG);
        element = setExpansionElement(element, iterative);
        return element;
    }



    private static final String NAME_ATTR = "name";
    private static final String DAG_TAG = "dag";
    @Override
    public Element parse(DAG dag) {
        try {
            Element dagRoot = document.createElement(DAG_TAG);
            dagRoot.setAttribute(NAME_ATTR, dag.getName());
            for (Node node : dag.getChildren()) {
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
                return map.get(icon.getType()).parse(icon);
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
