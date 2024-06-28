package com.textprocessingtool.textprocessingtool.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {
    @FXML
    private TextField findText;
    @FXML
    private TextField replaceText;
    @FXML
    private TextField regexPattern;
    @FXML
    private TextArea inputText;
    @FXML
    private TextFlow resultTextFlow;
    @FXML
    private RadioButton exactMatch;

    private ObservableList<String> matchList = FXCollections.observableArrayList();
    private Set<String> dataSet = new HashSet<>();

    @FXML
    private void handleFind() {
        String input = inputText.getText();
        String toFind = findText.getText();

        if (toFind.isEmpty()) {
            showAlert("Find field is empty");
            return;
        }

        matchList.clear();
        resultTextFlow.getChildren().clear();

        String regex = exactMatch.isSelected() ? "\\b" + Pattern.quote(toFind) + "\\b" : Pattern.quote(toFind);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int lastIndex = 0;
        while (matcher.find()) {
            resultTextFlow.getChildren().add(new Text(input.substring(lastIndex, matcher.start())));
            Text foundText = new Text(matcher.group());
            foundText.setStyle("-fx-fill: red;");
            resultTextFlow.getChildren().add(foundText);
            matchList.add("Word: " + matcher.group() + " at index: " + matcher.start());
            lastIndex = matcher.end();
        }
        resultTextFlow.getChildren().add(new Text(input.substring(lastIndex)));
    }

    @FXML
    private void handleReplace() {
        String input = inputText.getText();
        String toFind = findText.getText();
        String toReplace = replaceText.getText();

        if (toFind.isEmpty() || toReplace.isEmpty()) {
            showAlert("Find and/or replace fields are empty");
            return;
        }

        String regex = exactMatch.isSelected() ? "\\b" + Pattern.quote(toFind) + "\\b" : Pattern.quote(toFind);
        replaceText(input, regex, toReplace);
    }

    @FXML
    private void handleRegexReplace() {
        String input = inputText.getText();
        String patternText = regexPattern.getText();
        String toReplace = replaceText.getText();

        if (patternText.isEmpty() || toReplace.isEmpty()) {
            showAlert("Regex pattern and/or replace fields are empty");
            return;
        }

        replaceText(input, patternText, toReplace, true);
    }

    @FXML
    private void handleTestPattern() {
        String input = inputText.getText();
        String patternText = regexPattern.getText();

        if (patternText.isEmpty()) {
            showAlert("Regex pattern field is empty");
            return;
        }

        matchList.clear();
        resultTextFlow.getChildren().clear();

        try {
            Pattern pattern = Pattern.compile(patternText);
            Matcher matcher = pattern.matcher(input);

            int lastIndex = 0;
            while (matcher.find()) {
                resultTextFlow.getChildren().add(new Text(input.substring(lastIndex, matcher.start())));
                Text foundText = new Text(matcher.group());
                foundText.setStyle("-fx-fill: red;");
                resultTextFlow.getChildren().add(foundText);
                matchList.add("Word: " + matcher.group() + " at index: " + matcher.start());
                lastIndex = matcher.end();
            }
            resultTextFlow.getChildren().add(new Text(input.substring(lastIndex)));
        } catch (Exception e) {
            showAlert("Invalid regex pattern");
        }
    }

    private void replaceText(String input, String toFind, String toReplace) {
        String result = input.replaceAll(toFind, toReplace);
        inputText.setText(result);
    }

    private void replaceText(String input, String patternText, String toReplace, boolean isRegex) {
        try {
            Pattern pattern = Pattern.compile(patternText);
            Matcher matcher = pattern.matcher(input);
            String result = matcher.replaceAll(toReplace);
            inputText.setText(result);
        } catch (Exception e) {
            showAlert("Invalid regex pattern");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.show();
    }

    @FXML
    private void handleAddToCollection() {
        String input = inputText.getText();
        if (dataSet.add(input)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to collection");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Already in collection");
            alert.show();
        }
    }

    @FXML
    private void handleShowDataManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/textprocessingtool/textprocessingtool/DataLayout.fxml"));
            Parent root = loader.load();
            DataController dataController = loader.getController();
            dataController.setDataSet(dataSet);
            Stage stage = new Stage();
            stage.setTitle("Data Management Tool");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                inputText.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
