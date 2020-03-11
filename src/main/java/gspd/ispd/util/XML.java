package gspd.ispd.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileOutputStream;

public interface XML<E extends XML<E>> {
    Element toElement(Document document);
    E fromElement(Document document, Element element);
}
