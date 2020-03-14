package gspd.ispd.fxgui.commons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DiagramSelectionModel {

    //////////////////////////////////////
    //////////// CONSTRUCTOR /////////////
    //////////////////////////////////////

    private DiagramPane diagramPane;
    public DiagramSelectionModel(DiagramPane diagramPane) {
        this.diagramPane = diagramPane;
    }

    ///////////////////////////////////////
    ////////////// METHODS ////////////////
    ///////////////////////////////////////

    public boolean select(Icon icon) {
        if (diagramPane.has(icon)) {
            if (!isSelected(icon)) {
                selectedIcons.add(icon);
                icon.setSelected(true);
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(Icon icon) {
        return selectedIcons.contains(icon);
    }

    public boolean isEmpty() {
        return selectedIcons.isEmpty();
    }

    public boolean unselect(Icon icon) {
        if (isSelected(icon)) {
            selectedIcons.remove(icon);
            icon.setSelected(false);
            return true;
        }
        return false;
    }

    public boolean toggle(Icon icon) {
        if (isSelected(icon)) {
            return unselect(icon);
        } else {
            return select(icon);
        }
    }

    public boolean clear() {
        try {
            while (!selectedIcons.isEmpty()) {
                unselect(selectedIcons.iterator().next());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clearAndSelect(Icon icon) {
        if (clear()) {
            return select(icon);
        }
        return false;
    }

    public void translateSelectedIcons(double x, double y) {
        getSelectedIcons()
            .stream()
            .filter(icon -> icon.getIconType().isTypeOf(NodeIcon.NODE_TYPE))
            .forEach(icon -> {
                NodeIcon ni = (NodeIcon) icon;
                ni.setCenterX(ni.getCenterX() + x);
                ni.setCenterY(ni.getCenterY() + y);
            });
    }


    //////////////////////////////////////////////////
    ///////////////// PROPERTIES /////////////////////
    //////////////////////////////////////////////////

    /**
     * The selected icons so far
     */
    private ObservableList<Icon> selectedIcons = FXCollections.observableArrayList();
    public ObservableList<Icon> getSelectedIcons() {
        return FXCollections.unmodifiableObservableList(selectedIcons);
    }

}
