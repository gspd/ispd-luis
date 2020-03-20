package gspd.ispd.fxgui.workload.dag.icons;

import gspd.ispd.fxgui.commons.Icon;
import gspd.ispd.fxgui.commons.IconType;
import gspd.ispd.fxgui.commons.NodeIcon;
import gspd.ispd.fxgui.workload.dag.editor.TaskEditor;
import gspd.ispd.fxgui.workload.dag.shapes.TaskShape;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class TaskIcon extends NodeIcon {

    public static final IconType TASK_TYPE = IconType.type(NODE_TYPE, "TASK_TYPE");

    /////////////////////////////////////
    ////////// CONSTRUCTOR //////////////
    /////////////////////////////////////

    public TaskIcon(boolean selected, double centerX, double centerY) {
        super(TaskShape::new, selected, centerX, centerY);
        setType(TASK_TYPE);

        lockProperty().addListener((obs, o, n) -> {
            ((TaskShape) getContent()).setLabel(n == null ? "" : n);
        });
    }

    public TaskIcon(double centerX, double centerY) {
        this(false, centerX, centerY);
    }

    public TaskIcon(boolean selected) {
        this(selected, 0.0, 0.0);
    }

    public TaskIcon() {
        this(false, 0.0, 0.0);
    }

    ///////////////////////////////////////////////////
    ///////////////// OVERRIDES ///////////////////////
    ///////////////////////////////////////////////////

    @Override
    public Builder<? extends Icon> iconBuilder() {
        return TaskIcon::new;
    }

    @Override
    protected void updateIcon() {
        TaskShape shape = (TaskShape) getContent();
        if (isSelected()) {
            shape.setStroke(Color.BLUE);
            shape.setFill(Color.DEEPSKYBLUE);
        } else if (isHovered()) {
            shape.setFill(Color.LIGHTBLUE);
            shape.setStroke(Color.CYAN);
        } else {
            shape.setStroke(Color.BLACK);
            shape.setFill(Color.LIGHTYELLOW);
        }
    }

    /////////////////////////////////////////////////////
    ///////////////// PROPERTIES ////////////////////////
    /////////////////////////////////////////////////////

    /**
     * The computing power property
     */
    private DoubleProperty computingSize = new SimpleDoubleProperty(this, "computingSize", 0.0);
    public double getComputingSize() {
        return computingSize.get();
    }
    public DoubleProperty computingSizeProperty() {
        return computingSize;
    }
    public void setComputingSize(double computingSize) {
        this.computingSize.set(computingSize);
    }

    /**
     * The label property
     */
    private StringProperty label = new SimpleStringProperty(this, "label", null);
    public String getLabel() {
        return label.get();
    }
    public StringProperty labelProperty() {
        return label;
    }
    public void setLabel(String label) {
        this.label.set(label);
    }

    /**
     * The lock property
     */
    private StringProperty lock = new SimpleStringProperty(this, "lock", null);
    public String getLock() {
        return lock.get();
    }
    public StringProperty lockProperty() {
        return lock;
    }
    public void setLock(String lock) {
        this.lock.set(lock);
    }

    /**
     * The task editor
     */
    private static final TaskEditor EDITOR = new TaskEditor();
    @Override
    public TaskEditor editor() {
        EDITOR.setIcon(this);
        return EDITOR;
    }
}
