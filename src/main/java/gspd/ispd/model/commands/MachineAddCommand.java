package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.data.Machine;
import gspd.ispd.util.Command;

public class MachineAddCommand implements Command {

    private ISPDModel model;
    private Machine machine;

    public MachineAddCommand(ISPDModel model, Machine machine) {
        this.model = model;
        this.machine = machine;
    }

    @Override
    public void execute() {

    }
}
