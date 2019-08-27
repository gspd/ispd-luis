package gspd.ispd.fxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.ResourceBundle;

import gspd.ispd.ISPD;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
    @FXML
    private Hyperlink hardwareMousePointerIcon;
    @FXML
    private Hyperlink workloadMousePointerIcon;

    public void init() {
        setCompactView();
        setTooltips();
    }

    public void setCompactView() {
        setIconsTypeAsCompact();
    }

    public void setFullView() {
        setIconsTypeAsFull();
    }

    public void setTooltips() {
        setHardwareIconsTooltip();
        setWorkloadIconsTooltip();
        setMousePointerTooltip();
    }

    private void setIconsTypeAsCompact() {
        setHardwareIconsAsCompact();
        setWorkloadIconsAsCompact();
    }

    private void setHardwareIconsAsCompact() {
        machineIcon.setText("");
        linkIcon.setText("");
        clusterIcon.setText("");
        switchIcon.setText("");
    }

    private void setWorkloadIconsAsCompact() {
        taskIcon.setText("");
        dependencyIcon.setText("");
        messageIcon.setText("");
        synchronizationIcon.setText("");
        dataIcon.setText("");
        iterationIcon.setText("");
        parallelIcon.setText("");
    }

    private void setIconsTypeAsFull() {
        setHardwareIconsAsFull();
        setWorkloadIconsAsFull();
    }

    private void setHardwareIconsAsFull() {
        ResourceBundle strings = ISPD.getStrings();
        machineIcon.setText(strings.getString("main.hardware.machine"));
        linkIcon.setText(strings.getString("main.hardware.link"));
        clusterIcon.setText(strings.getString("main.hardware.cluster"));
        switchIcon.setText(strings.getString("main.hardware.switch"));
    }

    private void setWorkloadIconsAsFull() {
        ResourceBundle strings = ISPD.getStrings();
        taskIcon.setText(strings.getString("main.workload.task"));
        dependencyIcon.setText(strings.getString("main.workload.dependency"));
        messageIcon.setText(strings.getString("main.workload.message"));
        synchronizationIcon.setText(strings.getString("main.workload.synchronization"));
        dataIcon.setText(strings.getString("main.workload.data"));
        iterationIcon.setText(strings.getString("main.workload.iteration"));
        parallelIcon.setText(strings.getString("main.workload.parallel"));
    }

    private void setHardwareIconsTooltip() {
        ResourceBundle strings = ISPD.getStrings();
        machineIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.machine")));
        linkIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.link")));
        clusterIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.cluster")));
        switchIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.hardware.switch")));
    }

    private void setWorkloadIconsTooltip() {
        ResourceBundle strings = ISPD.getStrings();
        taskIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.task")));
        dependencyIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.dependency")));
        messageIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.message")));
        synchronizationIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.synchronization")));
        dataIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.data")));
        iterationIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.iteration")));
        parallelIcon.setTooltip(new Tooltip(strings.getString("tooltip.main.workload.parallel")));
    }

    private void setMousePointerTooltip() {
        Tooltip tooltip = new Tooltip(ISPD.getStrings().getString("tooltip.main.mouse"));
        hardwareMousePointerIcon.setTooltip(tooltip);
        workloadMousePointerIcon.setTooltip(tooltip);
    }
}
