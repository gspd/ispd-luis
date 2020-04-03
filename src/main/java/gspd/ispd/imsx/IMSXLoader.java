package gspd.ispd.imsx;

import org.w3c.dom.Element;

public interface IMSXLoader<T> {
    T load(Element element) throws IMSXLoadException;
}
