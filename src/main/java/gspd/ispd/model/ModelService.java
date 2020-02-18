package gspd.ispd.model;

import gspd.ispd.model.commands.*;
import gspd.ispd.model.data.Link;
import gspd.ispd.model.data.Machine;
import gspd.ispd.model.data.User;
import gspd.ispd.model.data.VM;

public class ModelService {

    private static ModelService service;

    private ISPDModel model;

    private ModelService() { }

    public static ModelService getInstance() {
        if (service == null) {
            synchronized (ModelService.class) {
                if (service == null) {
                    service = new ModelService();
                }
            }
        }
        return service;
    }

    public void setModel(ISPDModel model) {
        this.model = model;
    }

    public ISPDModel getModel() {
        return model;
    }

    public void add(Machine machine) {
        new MachineAddCommand(model, machine).execute();
    }

    public void add(User user) {
        new UserAddCommand(model, user).execute();
    }

    public void add(VM vm) {
        new VmAddCommand(model, vm).execute();
    }

    public void add(Link link) {
        new LinkAddCommand(model, link).execute();
    }

    public void save(String filename) {
        SaveCommand command = new SaveCommand(model, filename);
        command.execute();
        boolean result = command.getResult();
        if (result) {
            // notify save success
        } else {
            // notify error while saving
        }
    }

    public void load(String filename) {
        OpenFileCommand command = new OpenFileCommand(filename);
        command.execute();
        ISPDModel model = command.getResult();
        if (model != null) {
            this.model = model;
        } else {
            // notify error
        }
    }

    public boolean changeUsername(User user, String newUsername) {
        EditUsernameCommand command = new EditUsernameCommand(model, user, newUsername);
        command.execute();
        return command.getResult();
    }

    public String generateUsername(String basename) {
        GenerateUsernameCommand command = new GenerateUsernameCommand(model, basename);
        command.execute();
        return command.getResult();
    }

    public String generateUsername() {
        return generateUsername("unnamed");
    }

    public void executeCommand(String command) {
        new ExecuteCommand(model, command).execute();
    }

    public void removeUser(User user) {
        new RemoveUserCommand(model, user).execute();
    }
}
