package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.data.VM;
import gspd.ispd.util.Command;

public class VmAddCommand implements Command {

    private ISPDModel model;
    private VM vm;

    public VmAddCommand(ISPDModel model, VM vm) {
        this.model = model;
        this.vm = vm;
    }

    @Override
    public void execute() {

    }
}
