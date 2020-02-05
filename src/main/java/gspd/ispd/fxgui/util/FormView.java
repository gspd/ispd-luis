package gspd.ispd.fxgui.util;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FormView<E> extends GridPane {

    private int row;
    private E element;

    public FormView() {
        row = 0;
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(constraints);
        getColumnConstraints().add(constraints);
    }

    public void addInput(String labelText, Control input) {
        Label label = new Label(labelText);
        add(label, 0, row);
        add(input, 1, row);
        row += 1;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }
}
