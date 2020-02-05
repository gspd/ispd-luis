package gspd.ispd.fxgui;

import javafx.scene.image.ImageView;


public abstract class Icon<E extends Cloneable> extends ImageView implements Cloneable {
    private E element;

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
