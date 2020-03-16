package gspd.ispd.imsx.fxgui.dag;

import gspd.ispd.fxgui.commons.EdgeIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class EdgeIconParser<T extends EdgeIcon> extends IconParser<T> {

    private static final String CONNECT_TAG = "connect";
    private static final String START_ATTR = "start";
    private static final String END_ATTR = "end";

    @Override
    public Element toElement(Document document, T data) throws Exception {
        Element element = super.toElement(document, data);
        Element connect = document.createElement(CONNECT_TAG);
        connect.setAttribute(START_ATTR, data.getStartIcon().getIconID());
        connect.setAttribute(END_ATTR, data.getEndIcon().getIconID());
        element.appendChild(connect);
        return element;
    }

    @Override
    public T fromElement(Element element) throws Exception {
        return super.fromElement(element);
    }
}
