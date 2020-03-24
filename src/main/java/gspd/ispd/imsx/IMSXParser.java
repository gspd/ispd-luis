package gspd.ispd.imsx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IMSXParser<E> {

    public Element parse(E data);
}
