package gspd.ispd.fxgui.simples;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelpPaneController {
    /**
     * URL of the help file
     */
    private StringProperty url = new SimpleStringProperty(this, "url", null);

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }
}
