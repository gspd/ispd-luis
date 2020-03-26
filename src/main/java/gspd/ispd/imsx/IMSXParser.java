package gspd.ispd.imsx;

import org.w3c.dom.Element;

public interface IMSXParser<E> {

    Element parse(E data);
}
