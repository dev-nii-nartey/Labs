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

    private Set<String> dataSet = new HashSet<>();

    @FXML
    private void handleFind() {
        if (findText.getText().isEmpty()) {
            showAlert("Find field is empty");
            return;
        }
        performFind();
    }

    @FXML
    private void handleReplace() {
        if (findText.getText().isEmpty() && regexPattern.getText().isEmpty()) {
            showAlert("Find and Regex fields are empty");
            return;
        }
        if (!findText.getText().isEmpty()) {
            performReplace(findText.getText(), replaceText.getText());
        }
        if (!regexPattern.getText().isEmpty()) {
            performReplaceWithPattern(regexPattern.getText(), replaceText.getText());
        }
    }

    @FXML
    private void handleRegexReplace() {
        if (regexPattern.getText().isEmpty()) {
            showAlert("Regex field is empty");
            return;
        }
        performReplaceWithPattern(regexPattern.getText(), replaceText.getText());
    }

    @FXML
    private void handleTestPattern() {
        if (regexPattern.getText().isEmpty()) {
            showAlert("Regex field is empty");
            return;
        }
        performFindWithPattern();
    }

    private void performFind() {
        String input = inputText.getText();
        String toFind = findText.getText();
        boolean matchExact = exactMatch.isSelected();

        resultTextFlow.getChildren().clear();

        Pattern pattern = matchExact ? Pattern.compile("\\b" + Pattern.quote(toFind) + "\\b") : Pattern.compile(Pattern.quote(toFind));
        Matcher matcher = pattern.matcher(input);

        int lastIndex = 0;
        while (matcher.find()) {
            resultTextFlow.getChildren().add(new Text(input.substring(lastIndex, matcher.start())));
            Text foundText = new Text(matcher.group());
            foundText.setStyle("-fx-fill: red;");
            resultTextFlow.getChildren().add(foundText);
            lastIndex = matcher.end();
        }
        resultTextFlow.getChildren().add(new Text(input.substring(lastIndex)));
    }

    private void performReplace(String toFind, String toReplace) {
        String input = inputText.getText();
        boolean matchExact = exactMatch.isSelected();

        Pattern pattern = matchExact ? Pattern.compile("\\b" + Pattern.quote(toFind) + "\\b") : Pattern.compile(Pattern.quote(toFind));
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(result, toReplace);
        }
        matcher.appendTail(result);

        inputText.setText(result.toString());
    }

    private void performFindWithPattern() {
        String input = inputText.getText();
        String patternText = regexPattern.getText();

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
                lastIndex = matcher.end();
            }
            resultTextFlow.getChildren().add(new Text(input.substring(lastIndex)));
        } catch (Exception e) {
            showAlert("Invalid regex pattern");
        }
    }

    private void performReplaceWithPattern(String patternText, String toReplace) {
        String input = inputText.getText();

        try {
            Pattern pattern = Pattern.compile(patternText);
            Matcher matcher = pattern.matcher(input);
            StringBuffer result = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(result, toReplace);
            }
            matcher.appendTail(result);

            inputText.setText(result.toString());
        } catch (Exception e) {
            showAlert("Invalid regex pattern");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
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
            stage.initOwner(inputText.getScene().getWindow());
            stage.setUserData(this);  // Pass the main controller to the new stage
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

    // Add this method
    public void setInputText(String text) {
        inputText.setText(text);
    }
}
