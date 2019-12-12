package gspd.ispd.fxgui.draw;

import javafx.scene.Group;

public class DrawingArea extends Group {
    private enum State {
        SELECTING,
        MOVING,
        ADDING
    }

    State state;
    State previousState;

    public DrawingArea() {
        init();
    }

    public void init() {
        setOnMouseClicked(event -> {
            if (state == State.ADDING) {
                // draw something
            }
        });
        setOnMouseDragEntered(event -> {
            if (state == State.MOVING) {
                // moves something
            } else if (state == State.ADDING) {
                state = State.SELECTING;
                previousState = State.ADDING;
            }
        });
        setOnMouseDragExited(event -> {
            if (previousState == State.ADDING);
        });
    }
}
