package gspd.ispd.fxgui.commons;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;

public abstract class NodeShape extends IconShape {


    ///////////////////////////////////////////
    ///////////// PROPERTIES //////////////////
    ///////////////////////////////////////////

    /**
     * The fill property
     */
    private ObjectProperty<Paint> fill = new SimpleObjectProperty<>(this, "fill", null);
    public Paint getFill() {
        return fill.get();
    }
    public ObjectProperty<Paint> fillProperty() {
        return fill;
    }
    public void setFill(Paint fill) {
        this.fill.set(fill);
    }

    /**
     * The stroke property
     */
    private ObjectProperty<Paint> stroke = new SimpleObjectProperty<>(this, "stroke", null);
    public Paint getStroke() {
        return stroke.get();
    }
    public ObjectProperty<Paint> strokeProperty() {
        return stroke;
    }
    public void setStroke(Paint stroke) {
        this.stroke.set(stroke);
    }
}
