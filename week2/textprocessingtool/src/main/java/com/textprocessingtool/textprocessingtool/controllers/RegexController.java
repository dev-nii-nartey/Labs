package com.textprocessingtool.textprocessingtool.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;


public class RegexController {
    @FXML
    private TextArea textArea;

    @FXML
    private void handleInsertPattern() {
        // Open dialog to insert common regex pattern
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insert Regex Pattern");
        dialog.setHeaderText("Insert a common regex pattern");
        dialog.setContentText("Pattern:");

        dialog.showAndWait().ifPresent(pattern -> {
            textArea.insertText(textArea.getCaretPosition(), pattern);
        });
    }

    @FXML
    private void handleTestPattern() {
        // Open dialog to input regex pattern
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Test Regex Pattern");
        dialog.setHeaderText("Test a regex pattern on the text");
        dialog.setContentText("Pattern:");

        dialog.showAndWait().ifPresent(pattern -> {
            String content = textArea.getText();
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(content);

            if (matcher.find()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Pattern Match");
                alert.setHeaderText(null);
                alert.setContentText("Pattern matched: " + matcher.group());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Pattern Match");
                alert.setHeaderText(null);
                alert.setContentText("No match found.");
                alert.showAndWait();
            }
        });
    }
}

