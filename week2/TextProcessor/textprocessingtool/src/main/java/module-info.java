module com.textprocessingtool.textprocessingtool {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.textprocessingtool.textprocessingtool to javafx.fxml;
    exports com.textprocessingtool.textprocessingtool;
    exports com.textprocessingtool.textprocessingtool.controllers;
    opens com.textprocessingtool.textprocessingtool.controllers to javafx.fxml;
}