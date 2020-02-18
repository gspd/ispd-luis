package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.util.ResultCommand;

public class OpenFileCommand implements ResultCommand<ISPDModel> {

    private String filename;

    public OpenFileCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public ISPDModel getResult() {
        return null;
    }

    @Override
    public void execute() {

    }
}
