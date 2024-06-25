package com.textprocessingtool.textprocessingtool.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class DatabaseController {
    private Connection connect() {
        // MySQL connection
        String url = "jdbc:mysql://localhost:3306/text_processor";
        String user = "root";
        String password = "password";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    @FXML
    private TextArea textArea;

    @FXML
    private void createEntry(String data) {
        String sql = "INSERT INTO text_data(data) VALUES(?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            pstmt.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Database Entry");
            alert.setHeaderText(null);
            alert.setContentText("Entry created.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void readEntries() {
        String sql = "SELECT * FROM text_data";
        StringBuilder entries = new StringBuilder();

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                entries.append(rs.getInt("id")).append(": ").append(rs.getString("data")).append("\n");
            }
            textArea.setText(entries.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void updateEntry(int id, String newData) {
        String sql = "UPDATE text_data SET data = ? WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Database Entry");
            alert.setHeaderText(null);
            alert.setContentText("Entry updated.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void deleteEntry(int id) {
        String sql = "DELETE FROM text_data WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Database Entry");
            alert.setHeaderText(null);
            alert.setContentText("Entry deleted.");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

