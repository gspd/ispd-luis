package gspd.ispd.imsx.fxgui.dag;

import gspd.ispd.fxgui.commons.NodeIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class NodeIconParser<T extends NodeIcon> extends IconParser<T> {

    private static final String X_ATTR = "x";
    private static final String Y_ATTR = "y";
    private static final String POSITION_TAG = "position";

    @Override
    public Element toElement(Document document, T data) throws Exception {
        Element element = super.toElement(document, data);
        Element position = document.createElement(POSITION_TAG);
        position.setAttribute(X_ATTR, String.valueOf(data.getCenterX()));
        position.setAttribute(Y_ATTR, String.valueOf(data.getCenterY()));
        element.appendChild(position);
        return element;
    }

    @Override
    public T fromElement(Element element) throws Exception {
        return super.fromElement(element);
    }
}
