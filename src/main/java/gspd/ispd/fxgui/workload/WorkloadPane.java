package gspd.ispd.fxgui.workload;

import gspd.ispd.fxgui.commons.SlidePane;
import gspd.ispd.gui.JPrincipal;
import gspd.ispd.gui.iconico.grade.DesenhoGrade;
import gspd.ispd.util.workload.WorkloadConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class WorkloadPane extends SlidePane {


    public WorkloadPane() {
        createUI();
        loadFirstPane();
        desenhoGradeProperty().addListener((obs, o, n) -> {
            if (n != null) {
                WorkloadConverter.setupPane(this, n.getWorkloadGenerator());
            }
        });
    }

    private Button nextButton;
    private Button cancelButton;
    private Button okButton;
    private Button previousButton;
    private void createUI() {
        traceOrRandomPane = new TraceOrRandomPane();
        tracePane = new TracePane();
        traceFilePane = new TraceFilePane();
        generatePane = new GenerateWorkloadPane();
        generatePane.desenhoGradeProperty().bind(desenhoGradeProperty());
        nextButton = new Button("Next");
        cancelButton = new Button("Cancel");
        okButton = new Button("OK");
        okButton.setDefaultButton(true);
        previousButton = new Button("Previous");
    }

    private TraceOrRandomPane traceOrRandomPane;
    public void loadFirstPane() {
        setContent(traceOrRandomPane);
        nextButton.setOnAction(e -> {
            if (traceOrRandomPane.getChoice() == TraceOrRandomPane.TRACE) {
                loadTracePane();
            } else if (traceOrRandomPane.getChoice() == TraceOrRandomPane.GENERATE) {
                loadGeneratePane();
            }
        });
        setRightButton(nextButton);
        setLeftButton(cancelButton);
    }

    private TracePane tracePane;
    public void loadTracePane() {
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
    public void loadTraceFile() {
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
    public void loadGeneratePane() {
        setContent(generatePane);
        okButton.setDisable(false);
        okButton.setOnAction(e -> {
            getDesenhoGrade().setWorkloadGenerator(WorkloadConverter.toGenerator(this));
            getjPrincipal().getWorkloadPaneFrame().setVisible(false);
        });
        setRightButton(okButton);
        previousButton.setOnAction(e -> {
            loadFirstPane();
        });
        setLeftButton(previousButton);
    }

    ///////////////////////////////////
    /////////// ACCESSORS /////////////
    ///////////////////////////////////

    public TraceOrRandomPane getTraceOrRandomPane() {
        return traceOrRandomPane;
    }

    public GenerateWorkloadPane getGeneratePane() {
        return generatePane;
    }

    public TracePane getTracePane() {
        return tracePane;
    }

    public TraceFilePane getTraceFilePane() {
        return traceFilePane;
    }

    ///////////////////////////////////
    /////////// EXTERNALS /////////////
    ///////////////////////////////////

    // It is needed because, as we are mixing swing and javafx, we need
    // to be able to close swing workload frame from here
    private JPrincipal jPrincipal;
    public JPrincipal getjPrincipal() {
        return jPrincipal;
    }
    public void setjPrincipal(JPrincipal jPrincipal) {
        this.jPrincipal = jPrincipal;
    }

    ///////////////////////////////////
    /////////// PROPERTIES ////////////
    ///////////////////////////////////
    private ObjectProperty<DesenhoGrade> desenhoGrade = new SimpleObjectProperty<>(this, "desenhoGrade", null);
    public DesenhoGrade getDesenhoGrade() {
        return desenhoGrade.get();
    }
    public ObjectProperty<DesenhoGrade> desenhoGradeProperty() {
        return desenhoGrade;
    }
    public void setDesenhoGrade(DesenhoGrade desenhoGrade) {
        this.desenhoGrade.set(desenhoGrade);
    }
}
