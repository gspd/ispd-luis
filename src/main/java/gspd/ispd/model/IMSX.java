package gspd.ispd.model;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * IMSX file extension loader, and IMSX annotation processor
 *
 * @author Luís Baldissera
 */
public class IMSX {
    private ISPDModel model;

    public ISPDModel getModel() {
        return model;
    }

    public void setModel(ISPDModel model) {
        this.model = model;
    }
}
