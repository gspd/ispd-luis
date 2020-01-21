package gspd.ispd.fxgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MultipleSelectionModel;

public class NodeSelectionModel extends MultipleSelectionModel<Node> {

    private Parent parent;
    private ObservableList<Integer> selectedIndices;
    private ObservableList<Node> selectedItems;
    private Integer currentIndex;

    public NodeSelectionModel(Parent parent) {
        this.parent = parent;
        this.currentIndex = -1;
        this.selectedIndices = FXCollections.observableArrayList();
        this.selectedItems = parent.getChildrenUnmodifiable()
                .filtered(node -> selectedIndices.contains(parent.getChildrenUnmodifiable().indexOf(node)));
    }

    public NodeSelectionModel() {
        this(null);
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    private void decorate(int index) {
        Node node = parent.getChildrenUnmodifiable().get(index);

        node.setOpacity(0.5);
    }

    private void undecorate(int index) {
        Node node = parent.getChildrenUnmodifiable().get(index);
        node.setOpacity(1.0);
    }

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return selectedIndices;
    }

    @Override
    public ObservableList<Node> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public void selectIndices(int index, int... indices) {
        select(index);
        for (int i : indices) {
            select(i);
        }
    }

    @Override
    public void selectAll() {
        for (int i = 0; i < selectedIndices.size(); i++) {
            this.select(i);
        }
    }

    @Override
    public void selectFirst() {
        int index = 0;
        this.select(index);
    }

    @Override
    public void selectLast() {
        int index = selectedIndices.size() - 1;
        this.select(index);
    }

    @Override
    public void clearAndSelect(int index) {
        this.clearSelection();
        this.select(index);
    }

    @Override
    public void select(int index) {
        if (!selectedIndices.contains(index)) {
            currentIndex = index;
            selectedIndices.add(index);
            decorate(index);
        }
    }

    @Override
    public void select(Node obj) {
        int index = parent.getChildrenUnmodifiable().indexOf(obj);
        this.select(index);
    }

    @Override
    public void clearSelection(int index) {
        currentIndex = index;
        selectedIndices.remove(index);
        undecorate(index);
    }

    @Override
    public void clearSelection() {
        selectedIndices.forEach(this::clearSelection);
    }

    @Override
    public boolean isSelected(int index) {
        return selectedIndices.contains(index);
    }

    @Override
    public boolean isEmpty() {
        return selectedIndices.isEmpty();
    }

    @Override
    public void selectPrevious() {
        if (currentIndex > 0) {
            this.select(currentIndex - 1);
        }
    }

    @Override
    public void selectNext() {
        if (currentIndex < parent.getChildrenUnmodifiable().size() - 1) {
            this.select(currentIndex + 1);
        }
    }


}
