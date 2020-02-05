package gspd.ispd.model.data;

import gspd.ispd.annotations.FormField;
import gspd.ispd.annotations.FormViewer;
import gspd.ispd.annotations.IconImage;
import gspd.ispd.annotations.MiniInfo;

@FormViewer("Machine")
@IconImage("botao_no.gif")
public class MachineData {
    @FormField("label")
    private String label;
    @FormField("cores")
    @MiniInfo("C")
    private Integer cores;
    @FormField("power")
    @MiniInfo("P")
    private Double power;
    @FormField("memory")
    @MiniInfo("RAM")
    private Double memory;
    @FormField("storage")
    @MiniInfo("HD")
    private Double storage;
}
