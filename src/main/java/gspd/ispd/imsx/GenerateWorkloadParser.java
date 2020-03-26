package gspd.ispd.imsx;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.workload.GenerateWorkloadEntry;
import gspd.ispd.fxgui.workload.GenerateWorkloadPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateWorkloadParser implements IMSXParser<GenerateWorkloadPane> {

    private Document document;
    public GenerateWorkloadParser(Document document) {
        this.document = document;
    }

    @Override
    public Element parse(GenerateWorkloadPane data) {
        Element element = document.createElement(StringConstants.WORKLOAD_TAG);
        element.setAttribute(StringConstants.TYPE_ATTR, StringConstants.GENERATE_TYPE);
        GenerateWorkloadEntryParser entryParser = new GenerateWorkloadEntryParser(document);
        for (GenerateWorkloadEntry entry : data.getEntries()) {
            Element entryElement = entryParser.parse(entry);
            element.appendChild(entryElement);
        }
        return element;
    }
}
