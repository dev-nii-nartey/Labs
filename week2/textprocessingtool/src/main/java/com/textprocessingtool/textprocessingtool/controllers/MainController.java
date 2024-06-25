package com.textprocessingtool.textprocessingtool.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {
    @FXML
    private TextArea textArea;
    @FXML
    private Label statusLabel;

    private File currentFile;

    // File Menu Handlers
    @FXML
    private void handleNew() {
        textArea.clear();
        statusLabel.setText("New file created.");
        currentFile = null;
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Load file content into textArea
            // Update statusLabel and currentFile
        }
    }

    @FXML
    private void handleSave() {
        if (currentFile != null) {
            // Save textArea content to currentFile
            statusLabel.setText("File saved.");
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // Save textArea content to file
            statusLabel.setText("File saved as " + file.getName());
            currentFile = file;
        }
    }

    @FXML
    private void handleExit() {
        // Handle application exit

        System.exit(0);
    }

    // Edit Menu Handlers
    @FXML
    private void handleUndo() {
        textArea.undo();
        statusLabel.setText("Undo operation performed.");
    }

    @FXML
    private void handleRedo() {
        textArea.redo();
        statusLabel.setText("Redo operation performed.");
    }

    @FXML
    private void handleCut() {
        textArea.cut();
        statusLabel.setText("Text cut to clipboard.");
    }

    @FXML
    private void handleCopy() {
        textArea.copy();
        statusLabel.setText("Text copied to clipboard.");
    }

    @FXML
    private void handlePaste() {
        textArea.paste();
        statusLabel.setText("Text pasted from clipboard.");
    }

    @FXML
    private void handleFind() {
        // Handle find operation
    }

    @FXML
    private void handleReplace() {
        // Handle replace operation
    }

    // Regex Menu Handlers
    @FXML
    private void handleInsertPattern() {
        // Insert common regex pattern
    }

    @FXML
    private void handleTestPattern() {
        // Test regex pattern on textArea content
    }

    // Help Menu Handlers
    @FXML
    private void handleAbout() {
        // Display about information
    }

    @FXML
    private void handleHelp() {
        // Display help documentation
    }
}