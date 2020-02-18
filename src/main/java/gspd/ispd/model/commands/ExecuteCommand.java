package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.util.Command;

public class ExecuteCommand implements Command {

    private ISPDModel model;
    private String command;

    public ExecuteCommand(ISPDModel model, String command) {
        this.model = model;
        this.command = command;
    }

    @Override
    public void execute() {

    }
}
