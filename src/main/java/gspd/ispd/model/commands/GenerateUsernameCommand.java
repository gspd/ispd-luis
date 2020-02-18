package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.util.ResultCommand;

public class GenerateUsernameCommand implements ResultCommand<String> {

    private ISPDModel model;
    private String basename;
    private String result;

    public GenerateUsernameCommand(ISPDModel model, String basename) {
        this.model = model;
        this.basename = basename;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = basename;
    }
}
