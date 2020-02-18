package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.util.ResultCommand;

public class SaveCommand implements ResultCommand<Boolean> {

    private ISPDModel model;
    private String filename;

    public SaveCommand(ISPDModel model, String filename) {
        this.model = model;
        this.filename = filename;
    }

    @Override
    public void execute() {

    }

    @Override
    public Boolean getResult() {
        return null;
    }
}
