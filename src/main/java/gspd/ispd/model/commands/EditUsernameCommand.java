package gspd.ispd.model.commands;

import gspd.ispd.model.ISPDModel;
import gspd.ispd.model.data.User;
import gspd.ispd.util.ResultCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditUsernameCommand implements ResultCommand<Boolean> {

    private ISPDModel model;
    private User user;
    private String newUsername;
    private Boolean result;

    public EditUsernameCommand(ISPDModel model, User user, String newUsername) {
        this.model = model;
        this.user = user;
        this.newUsername = newUsername;
    }

    @Override
    public Boolean getResult() {
        return result;
    }

    @Override
    public void execute() {
        user.setName(newUsername);
        result = true;
    }
}
