package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.data.Link;
import gspd.ispd.util.Command;

public class LinkAddCommand implements Command {

    private ISPDModel model;
    private Link link;

    public LinkAddCommand(ISPDModel model, Link link) {
        this.model = model;
        this.link = link;
    }

    @Override
    public void execute() {

    }
}
