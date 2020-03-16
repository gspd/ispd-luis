package gspd.ispd.imsx.fxgui.dag;

import gspd.ispd.fxgui.dag.icons.TaskIcon;
import gspd.ispd.imsx.IMSXParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TaskParser implements IMSXParser<TaskIcon> {

    private static final String TAG_NAME = "task";
    private static final String X_ATTR = "x";
    private static final String Y_ATTR = "y";
    private static final String SELECTED_ATTR = "selected";

    @Override
    public Element toElement(Document document, TaskIcon data) throws Exception {
        Element element = document.createElement(TAG_NAME);
        element.setAttribute(X_ATTR, String.valueOf(data.getCenterX()));
        element.setAttribute(Y_ATTR, String.valueOf(data.getCenterY()));
        element.setAttribute(SELECTED_ATTR, String.valueOf(data.isSelected()));
        return element;
    }

    @Override
    public TaskIcon fromElement(Element element) throws Exception {
        return null;
    }
}
