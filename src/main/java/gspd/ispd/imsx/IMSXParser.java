package gspd.ispd.imsx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IMSXParser<E> {

    Element toElement(Document document, E data) throws Exception;
    E fromElement(Element element) throws Exception;
}
