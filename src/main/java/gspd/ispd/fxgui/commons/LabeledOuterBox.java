package gspd.ispd.fxgui.commons;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LabeledOuterBox extends OuterBox {

    public LabeledOuterBox(String label) {
        Text text = new Text(label);
        text.fillProperty().bind(strokeProperty());
        setStroke(Color.BLACK);
        text.setFont(Font.font("Monospace", FontWeight.BOLD, 12.0));
        text.setTextAlignment(TextAlignment.LEFT);
        text.setTextOrigin(VPos.TOP);
        super.getChildren().add(text);
    }
}
