package gspd.ispd.fxgui.workload;

import gspd.ispd.fxgui.commons.SlidePane;
import gspd.ispd.fxgui.workload.dag.DAG;
import gspd.ispd.fxgui.workload.dag.DagEditor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class WorkloadPane extends SlidePane {

    public WorkloadPane() {
        createUI();
        loadFirstPane();
    }

    private Button nextButton;
    private Button cancelButton;
    private Button okButton;
    private Button previousButton;
    private void createUI() {
        traceOrRandom = new TraceOrRandomPane();
        tracePane = new TracePane();
        traceFilePane = new TraceFilePane();
        dagStage = new Stage();
        dagStage.initModality(Modality.WINDOW_MODAL);
        nextButton = new Button("Next");
        cancelButton = new Button("Cancel");
        okButton = new Button("OK");
        okButton.setDefaultButton(true);
        previousButton = new Button("Previous");
    }

    private TraceOrRandomPane traceOrRandom;
    private void loadFirstPane() {
        setContent(traceOrRandom);
        nextButton.setOnAction(e -> {
            if (traceOrRandom.getChoice() == TraceOrRandomPane.TRACE) {
                loadTracePane();
            } else if (traceOrRandom.getChoice() == TraceOrRandomPane.GENERATE) {
                openEditor();
            }
        });
        setRightButton(nextButton);
        setLeftButton(cancelButton);
    }

    private TracePane tracePane;
    private void loadTracePane() {
        setContent(tracePane);
        nextButton.setOnAction(e -> {
            if (tracePane.getChoice() >= 0) {
                loadTraceFile();
            }
        });
        setRightButton(nextButton);
        previousButton.setOnAction(e -> {
            loadFirstPane();
        });
        setLeftButton(previousButton);
    }

    private TraceFilePane traceFilePane;
    private void loadTraceFile() {
        setContent(traceFilePane);
        okButton.setOnAction(e -> {
            System.out.println("You finished the configuration!");
        });
        okButton.disableProperty().bind(traceFilePane.fileProperty().isNull());
        setRightButton(okButton);
        previousButton.setOnAction(e -> {
            loadTracePane();
        });
        setLeftButton(previousButton);
    }

    private Stage dagStage;
    private void openEditor() {
        DagEditor dagEditor = new DagEditor();
        dagStage.setScene(new Scene(dagEditor));
        dagStage.showAndWait();
    }
}
