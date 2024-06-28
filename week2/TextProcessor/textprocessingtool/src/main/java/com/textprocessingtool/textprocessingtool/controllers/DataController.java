package com.textprocessingtool.textprocessingtool.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashSet;
import java.util.Set;

public class DataController {
    @FXML
    private TextField keyField;
    @FXML
    private TextField valueField;
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
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> keyField.setText(newValue));
    }

    @FXML
    private void handleUpdate() {
        String key = keyField.getText();
        String value = valueField.getText();

        if (dataSet.remove(key)) {
            dataSet.add(value);
            updateListView();
            statusLabel.setText("Updated successfully");
        } else {
            statusLabel.setText("Key not found");
        }
    }

    @FXML
    private void handleDelete() {
        String key = keyField.getText();
        if (dataSet.remove(key)) {
            updateListView();
            statusLabel.setText("Deleted successfully");
        } else {
            statusLabel.setText("Key not found");
        }
    }

    private void updateListView() {
        observableList.setAll(dataSet);
    }
}
