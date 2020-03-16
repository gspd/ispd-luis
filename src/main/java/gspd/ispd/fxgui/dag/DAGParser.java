package gspd.ispd.fxgui.dag;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

public class DAGParser {

    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    /////////////////////////////
    ////////// METHODS //////////
    /////////////////////////////

    public boolean load(String filename) {
        try {
            File file = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            log("Loading document");
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            log("Loading root element");
            Element root = document.getDocumentElement();
            log("root=" + root);
            NodeList nodeList = root.getElementsByTagName("trace");
            log("traceList=" + nodeList);
            Node trace = nodeList.item(0);
            log("trace=" + trace);
            Element traceElement = (Element) trace;
            log("(Element)trace=" + traceElement);
            NodeList formatList = traceElement.getElementsByTagName("format");
            log("formatList=" + formatList);
            Node format = formatList.item(0);
            log("format=" + format);
            Element formatElement = (Element) format;
            log("(Element)format=" + formatElement);
            String formatKind = formatElement.getAttribute("kind");
            log("format.kind=" + formatKind);
        } catch (Exception e) {
            System.out.println("Error while loading: " + e);
            return false;
        }
        return true;
    }

    private void log(String message) {
        System.out.println(BLUE + "[" + DAGParser.class.getName() + "]" + RESET + " " + message);
    }

    public boolean store(String filename) {
        try {
        } catch (Exception e) {
            System.out.println("Error while storing: " + e);
        }
        return true;
    }

    /////////////////////////////
    ///////// ATTRIBUTES ////////
    /////////////////////////////

    /**
     * The dag
     */
    private DAG dag;
    public DAG getDag() {
        return dag;
    }
    public void setDag(DAG dag) {
        this.dag = dag;
    }
}
