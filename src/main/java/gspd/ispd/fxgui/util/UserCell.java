package gspd.ispd.fxgui.util;

import gspd.ispd.fxgui.GUIUtil;
import gspd.ispd.model.ModelService;
import gspd.ispd.model.data.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * Defines the user list cell that will be used in list views of users
 */
public class UserCell extends ListCell<User> {

    private final static Image USER_O_IMG = new Image(GUIUtil.class.getResource("images/icons/fa-user-o.png").toExternalForm());
    private final static Image USER_IMG = new Image(GUIUtil.class.getResource("images/icons/fa-user.png").toExternalForm());

    private User editingUser;
    private CustomTextField editField;

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
            String tooltipString = item.getName() + '\n' +
                    "MAC: " + (item.getMachines() == null ? 0 : item.getMachines().size()) + '\n' +
                    "LNK: " + (item.getLinks() == null ? 0 : item.getLinks().size()) + '\n' +
                    "VMS: " + (item.getVms() == null ? 0 : item.getVms().size());
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
        editField.setRight(new CommitCancel(this));
        editField.setText(editingUser.getName());
        editField.selectAll();
        editField.setMinWidth(1.0);
        setText(null);
        setGraphic(editField);
        editField.requestFocus();
        setOnKeyPressed(this::handleKeys);
    }

    private void handleKeys(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            changeUsername();
            event.consume();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            event.consume();
        }
    }

    private void changeUsername() {
        if (service.changeUsername(editingUser, editField.getText())) {
            commitEdit(editingUser);
        } else {
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
        super.commitEdit(newValue);
        updateItem(newValue, newValue == null);
    }

    private static class CommitCancel extends HBox {

        private UserCell cell;

        private static final Image CHECK_IMG = new Image(GUIUtil.class.getResource("images/icons/fa-check.png").toExternalForm());
        private static final Image TIMES_IMG = new Image(GUIUtil.class.getResource("images/icons/fa-times.png").toExternalForm());

        private static final Tooltip commitTooltip = new Tooltip("Change username");
        private static final Tooltip cancelTooltip = new Tooltip("Cancel");

        public CommitCancel(UserCell cell) {
            this.cell = cell;

            Hyperlink commitLink = new Hyperlink("");
            commitLink.setGraphic(new ImageView(CHECK_IMG));
            commitLink.setOnAction(this::handleCommit);
            commitLink.setTooltip(commitTooltip);

            Hyperlink cancelLink = new Hyperlink("");
            cancelLink.setGraphic(new ImageView(TIMES_IMG));
            cancelLink.setOnAction(this::handleCancel);
            cancelLink.setTooltip(cancelTooltip);

            super.getChildren().addAll(cancelLink, commitLink);
        }

        private void handleCommit(ActionEvent event) {
            cell.changeUsername();
            event.consume();
        }

        private void handleCancel(ActionEvent event) {
            cell.cancelEdit();
            event.consume();
        }
    }
}
