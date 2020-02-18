package gspd.ispd.fxgui.simples;

import gspd.ispd.model.ModelService;
import gspd.ispd.model.data.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * Defines the user list cell that will be used in list views of users
 */
public class UserCell extends ListCell<User> {

    private final static Image USER_O_IMG = new Image(UserCell.class.getResource("fa-user-o.png").toExternalForm());
    private final static Image USER_IMG = new Image(UserCell.class.getResource("fa-user.png").toExternalForm());

    private static User editingUser;
    private static CustomTextField editField;

    private final static ModelService service = ModelService.getInstance();

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setTooltip(null);
        } else {
            setText(item.getName());
            ImageView iv = new ImageView(item.isEmpty() ? USER_O_IMG : USER_IMG);
            setGraphic(iv);
            String tooltipString = ""
                    + item.getName() + "\n"
                    + "MAC: " + ((item.getMachines() == null) ? 0 : item.getMachines().size()) + "\n"
                    + "LNK: " + ((item.getLinks() == null) ? 0 : item.getLinks().size()) + "\n"
                    + "VMS: " + ((item.getVms() == null) ? 0 : item.getVms().size());
            if (getTooltip() == null) {
                setTooltip(new Tooltip(tooltipString));
            } else {
                getTooltip().setText(tooltipString);
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        editingUser = super.getItem();
        editField = new CustomTextField();
        editField.setText(editingUser.getName());
        editField.selectAll();
        setText(null);
        setGraphic(editField);
        this.setOnKeyPressed(this::handleKeys);
    }

    private void handleKeys(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (service.changeUsername(editingUser, editField.getText())) {
                commitEdit(editingUser);
            } else {
                cancelEdit();
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
        }
    }

    @Override
    public void cancelEdit() {
        updateItem(editingUser, editingUser == null);
        super.cancelEdit();
    }

    @Override
    public void commitEdit(User newValue) {
        updateItem(editingUser, editingUser == null);
        super.commitEdit(newValue);
    }
}
