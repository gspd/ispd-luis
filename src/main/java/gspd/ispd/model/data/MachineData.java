package gspd.ispd.model.data;

import gspd.ispd.annotations.FormField;
import gspd.ispd.annotations.FormViewer;

@FormViewer("Machine")
public class MachineData {
    @FormField("label")
    private String label;
    @FormField("cores")
    private Integer cores;
    @FormField("power")
    private Double power;
    @FormField("memory")
    private Double memory;
    @FormField("storage")
    private Double storage;
}
