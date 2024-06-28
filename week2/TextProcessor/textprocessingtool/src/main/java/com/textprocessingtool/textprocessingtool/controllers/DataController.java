package com.textprocessingtool.textprocessingtool.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DataController {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label statusLabel;

    private Set<String> dataSet = new HashSet<>();
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public void setDataSet(Set<String> dataSet) {
        this.dataSet = dataSet;
        updateListView();
    }

    @FXML
    private void initialize() {
        listView.setItems(observableList);
    }

    @FXML
    private void handleEdit() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Stage stage = (Stage) listView.getScene().getWindow();
            MainController mainController = (MainController) stage.getUserData();
            mainController.setInputText(selectedItem);
            stage.close();
        } else {
            statusLabel.setText("Status: No item selected for editing");
        }
    }

    @FXML
    private void handleDelete() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            dataSet.remove(selectedItem);
            updateListView();
            statusLabel.setText("Status: Deleted successfully");
        } else {
            statusLabel.setText("Status: No item selected for deletion");
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            updateListView();
        } else {
            Set<String> filteredSet = dataSet.stream()
                    .filter(item -> item.contains(query))
                    .collect(Collectors.toSet());
            observableList.setAll(filteredSet);
        }
    }

    private void updateListView() {
        observableList.setAll(dataSet);
    }
}
