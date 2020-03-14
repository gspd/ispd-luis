package gspd.ispd.fxgui.dag.shapes;

import gspd.ispd.fxgui.commons.NodeShape;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TaskShape extends NodeShape {

    ///////////////////////////////////////////////
    ///////////////// CONSTRUCTOR /////////////////
    ///////////////////////////////////////////////

    public TaskShape(String label) {
        // LISTENERS
        labelProperty().addListener(this::update);
        // UI
        createContent();
        // SETs
        setLabel(label);
    }

    public TaskShape() {
        this("");
    }

    private Rectangle rectangle;
    private Text labelText;
    private void createContent() {
        rectangle = new Rectangle(20.0, 15.0);
        rectangle.strokeProperty().bind(strokeProperty());
        rectangle.fillProperty().bind(fillProperty());
        setStroke(Color.BLACK);
        setFill(Color.LIGHTYELLOW);
        labelText = new Text();
        labelText.setTextAlignment(TextAlignment.RIGHT);
        labelText.setTextOrigin(VPos.TOP);
        labelText.setWrappingWidth(20.0);
        labelText.setFont(Font.font(8.0));
        super.getChildren().setAll(rectangle, labelText);
    }

    ////////////////////////////////////////////////
    /////////////// EVENT HANDLERS /////////////////
    ////////////////////////////////////////////////

    private void update(Observable observable) {
        if (rectangle == null) {
            rectangle = new Rectangle();
        }
        if (labelText == null) {
            labelText = new Text();
        }
        if (getLabel().equals("") || getLabel() == null) {
            labelText.setText("");
        }
        labelText.setText("[" + getLabel() + "]");
    }

    ///////////////////////////////////////////////////
    //////////////// PROPERTIES ///////////////////////
    ///////////////////////////////////////////////////

    /**
     * The upper right label
     */
    private StringProperty label = new SimpleStringProperty(this, "label", "");
    public String getLabel() {
        return label.get();
    }
    public StringProperty labelProperty() {
        return label;
    }
    public void setLabel(String label) {
        this.label.set(label);
    }
}