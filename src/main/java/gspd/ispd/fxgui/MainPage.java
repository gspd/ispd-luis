package gspd.ispd.fxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

import gspd.ispd.ISPD;
import javafx.scene.control.TextField;

public class MainPage {
    @FXML
    private TextArea terminalOutputArea;
    @FXML
    private TextField terminalInputField;
    @FXML
    private TextArea helpArea;
    @FXML
    private Hyperlink machineIcon;
    @FXML
    private Hyperlink linkIcon;
    @FXML
    private Hyperlink clusterIcon;
    @FXML
    private Hyperlink switchIcon;
    @FXML
    private Hyperlink taskIcon;
    @FXML
    private Hyperlink dependencyIcon;
    @FXML
    private Hyperlink messageIcon;
    @FXML
    private Hyperlink synchronizationIcon;
    @FXML
    private Hyperlink dataIcon;
    @FXML
    private Hyperlink iterationIcon;
    @FXML
    private Hyperlink parallelIcon;

    private void setIconsTypeAsCompact() {
        machineIcon.setText("");
        linkIcon.setText("");
        clusterIcon.setText("");
        switchIcon.setText("");
        taskIcon.setText("");
        dependencyIcon.setText("");
        messageIcon.setText("");
        synchronizationIcon.setText("");
        dataIcon.setText("");
        iterationIcon.setText("");
        parallelIcon.setText("");
    }

    private void setIconsTypeAsFull() {
        machineIcon.setText(ISPD.getStrings().getString("main.hardware.machine"));
        linkIcon.setText(ISPD.getStrings().getString("main.hardware.link"));
        clusterIcon.setText(ISPD.getStrings().getString("main.hardware.cluster"));
        switchIcon.setText(ISPD.getStrings().getString("main.hardware.switch"));
        taskIcon.setText(ISPD.getStrings().getString("main.workload.task"));
        dependencyIcon.setText(ISPD.getStrings().getString("main.workload.dependency"));
        messageIcon.setText(ISPD.getStrings().getString("main.workload.message"));
        synchronizationIcon.setText(ISPD.getStrings().getString("main.workload.synchronization"));
        dataIcon.setText(ISPD.getStrings().getString("main.workload.data"));
        iterationIcon.setText(ISPD.getStrings().getString("main.workload.iteration"));
        parallelIcon.setText(ISPD.getStrings().getString("main.workload.parallel"));
    }

    private void hideTerminal() {
        terminalOutputArea.getParent().setVisible(false);
    }

    private void showTerminal() {
        terminalOutputArea.getParent().setVisible(true);
    }

    protected void setCompactView() {
        setIconsTypeAsCompact();
    }

    protected void setFullView() {
        setIconsTypeAsFull();
    }
}
