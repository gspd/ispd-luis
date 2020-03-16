package gspd.ispd.imsx.fxgui.dag;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.imsx.IMSXParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class IconParser<T extends Icon> implements IMSXParser<T> {

    private static final String TAG_NAME = "icon";
    private static final String TYPE_ATTR = "type";
    private static final String ID_ATTR = "id";

    @Override
    public Element toElement(Document document, T data) throws Exception {
        Element element = document.createElement(TAG_NAME);
        element.setAttribute(ID_ATTR, data.getIconID());
        element.setAttribute(TYPE_ATTR, data.getType().getName());
        return element;
    }

    @Override
    public T fromElement(Element element) throws Exception {
        return null;
    }
}
