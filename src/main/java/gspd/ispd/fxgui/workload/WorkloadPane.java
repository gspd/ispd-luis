package gspd.ispd.fxgui.workload;

import gspd.ispd.fxgui.commons.SlidePane;
import javafx.scene.control.Button;

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
        generatePane = new GenerateWorkloadPane();
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
                loadGeneratePane();
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

    private GenerateWorkloadPane generatePane;
    private void loadGeneratePane() {
        setContent(generatePane);
        okButton.setOnAction(e -> {
            System.out.println("You finished your configuration!");
        });
        setRightButton(okButton);
        previousButton.setOnAction(e -> {
            loadFirstPane();
        });
        setLeftButton(previousButton);
    }
}
