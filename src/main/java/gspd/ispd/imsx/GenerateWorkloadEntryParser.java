package gspd.ispd.imsx;

import gspd.ispd.commons.StringConstants;
import gspd.ispd.fxgui.workload.GenerateWorkloadEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateWorkloadEntryParser implements IMSXParser<GenerateWorkloadEntry> {

    private Document document;

    public GenerateWorkloadEntryParser(Document document) {
        this.document = document;
    }

    @Override
    public Element parse(GenerateWorkloadEntry data) {
        Element element = document.createElement(StringConstants.ENTRY_TAG);
        element.setAttribute(StringConstants.USER_ATTR, data.getUser());
        element.setAttribute(StringConstants.SCHEDULER_ATTR, data.getScheduler());
        element.setAttribute(StringConstants.QUANTITY_ATTR, String.valueOf(data.getQuantity()));
        element.setAttribute(StringConstants.ARRIVAL_ATTR, String.valueOf(data.getArrivalTime()));
        element.setAttribute(StringConstants.TYPE_ATTR, data.getType());
        element.setAttribute(StringConstants.DATA_ATTR, data.getData());
        return element;
    }
}
