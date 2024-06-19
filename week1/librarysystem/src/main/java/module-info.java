module com.librarymgt.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfoenix;
    requires java.sql;


    opens com.librarymgt.librarysystem to javafx.fxml;
    exports com.librarymgt.librarysystem;

}