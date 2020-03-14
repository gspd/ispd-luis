package gspd.ispd.fxgui.commons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Builder;

public class ImageViewBuilder implements Builder<ImageView> {

    private Image image;
    public ImageViewBuilder(Image image) {
        this.image = image;
    }

    @Override
    public ImageView build() {
        return new ImageView(image);
    }
}
