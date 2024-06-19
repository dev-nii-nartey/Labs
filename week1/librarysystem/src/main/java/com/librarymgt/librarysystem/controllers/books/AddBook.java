package com.librarymgt.librarysystem.controllers.books;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.librarymgt.librarysystem.database.DatabaseHandler;
import javafx.fxml.FXML;

import java.util.ResourceBundle;

public class AddBook {

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    DatabaseHandler databaseHandler;

    @FXML
    void addBook(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        DatabaseHandler = new DatabaseHandler();
    }

}
