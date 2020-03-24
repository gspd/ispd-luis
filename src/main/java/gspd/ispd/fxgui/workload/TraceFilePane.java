package gspd.ispd.fxgui.workload;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class TraceFilePane extends GridPane {
    
    public TraceFilePane() {
        createUI();
        setFile(null);
    }

    private TextField fileInput;
    private Button openButton;
    private void createUI() {
        fileInput = new TextField();
        openButton = new Button("Open");
        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("iSPD Traces", "*.wmsx")
            );
            setFile(fileChooser.showOpenDialog(null));
            if (getFile() != null) {
                fileInput.setText(getFile().getAbsolutePath());
            } else {
                fileInput.setText("");
            }
        });
        fileInput.textProperty().addListener((obs, o, n) -> {
            if (n == null || n.equals("")) {
                setFile(null);
            } else {
                File tempFile = new File(n);
                if (tempFile.exists() && tempFile.isFile()) {
                    setFile(tempFile);
                } else {
                    setFile(null);
                }
            }
        });
        Text text = new Text("Choose a file");
        add(text, 0, 0, 2, 1);
        add(fileInput, 0, 1);
        add(openButton, 1, 1);
    }

    ////////////////////////////////////
    ///////////// PROPERTIES ///////////
    ////////////////////////////////////

    private ObjectProperty<File> file = new SimpleObjectProperty<>(this, "file", null);
    public File getFile() {
        return file.get();
    }
    public ObjectProperty<File> fileProperty() {
        return file;
    }
    public void setFile(File file) {
        this.file.set(file);
    }
}
