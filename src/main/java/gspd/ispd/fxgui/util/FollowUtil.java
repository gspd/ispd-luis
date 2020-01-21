package gspd.ispd.fxgui.util;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class FollowUtil {

    public void makeFollow(Node node, Pane pane) {
        pane.setCursor(Cursor.CROSSHAIR);
        pane.setOnMouseEntered(event -> {
            double width, height;
            width = node.getBoundsInLocal().getWidth();
            height = node.getBoundsInLocal().getHeight();
            pane.getChildren().add(node);
            pane.setOnMouseMoved(e -> {
                node.setLayoutX(e.getX() - width / 2);
                node.setLayoutY(e.getY() - height / 2);
            });
        });
        pane.setOnMouseExited(event -> {
            pane.getChildren().remove(node);
            pane.setOnMouseMoved(e -> {});
        });
    }

    public void lockFollow(Parent pane) {
        pane.setCursor(Cursor.DEFAULT);
        pane.setOnMouseEntered(event -> {});
        pane.setOnMouseExited(event -> {});
    }
}
