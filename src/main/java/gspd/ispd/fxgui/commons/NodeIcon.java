package gspd.ispd.fxgui.commons;

import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Builder;

public class NodeIcon extends Icon {

    public NodeIcon(Builder<? extends Node> nodeBuilder, boolean selected) {
        super(nodeBuilder, selected);
    }

    public NodeIcon(Builder<? extends Node> nodeBuilder) {
        this(nodeBuilder, false);
    }

    ////////////////////////////////////////////////
    ////////////// STATIC BUILDERS /////////////////
    ////////////////////////////////////////////////

    public static final Builder<Node> TASK_BUILDER = new Builder<Node>() {
        @Override
        public Node build() {
            Rectangle rectangle = new Rectangle(20, 15);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTYELLOW);
            return rectangle;
        }
    };
    public static final Builder<Node> SYNCHRONIZE_BUILDER = new Builder<Node>() {
        @Override
        public Node build() {
            Circle circle = new Circle(8);
            circle.setFill(Color.BLACK);
            circle.setStroke(Color.BLACK);
            return circle;
        }
    };
    public static final Builder<Node> ACTIVATION_BUILDER = new Builder<Node>() {
        @Override
        public Node build() {
            Circle circle = new Circle(8);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            return circle;
        }
    };
    public static final Builder<Node> SWITCH_BUILDER = new Builder<Node>() {
        @Override
        public Node build() {
            Path path = new Path(
                    new MoveTo(0, 0),
                    new LineTo(18, -9),
                    new LineTo(18, 9),
                    new ClosePath()
            );
            path.setStroke(Color.BLACK);
            path.setFill(Color.BLACK);
            return path;
        }
    };
    public static final Builder<Node> TIMER_BUILDER = new Builder<Node>() {
        @Override
        public Node build() {
            Circle circle = new Circle(10);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.LIGHTGREEN);
            return circle;
        }
    };

    ///////////////////////////////////////////////////
    //////////////// OVERRIDES ////////////////////////
    ///////////////////////////////////////////////////

    private Rectangle selection;
    private static final Lighting lighting;
    static {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        lighting = new Lighting(light);
        lighting.setSurfaceScale(5);
    }
    @Override
    protected void updateSelection(boolean selected) {
        if (selected) {
            selection = new Rectangle();
            selection.setWidth(getContent().getLayoutBounds().getWidth() + 2.0);
            selection.setHeight(getContent().getLayoutBounds().getHeight() + 2.0);
            selection.setX(getContent().getLayoutBounds().getMinX() - 1.0);
            selection.setY(getContent().getLayoutBounds().getMinY() - 1.0);
            selection.setFill(null);
            selection.setStroke(Color.LIGHTGREEN);
            selection.setStrokeWidth(2.0);
            getContent().setEffect(lighting);
            super.getChildren().add(selection);
        } else {
            getContent().setEffect(null);
            super.getChildren().remove(selection);
            selection = null;
        }
    }

}
