module taas_tech.librarymanagementsystem {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;

        opens taas_tech.librarymanagementsystem to javafx.fxml;
        exports taas_tech.librarymanagementsystem;
        exports taas_tech.librarymanagementsystem.library.guiController;
        opens taas_tech.librarymanagementsystem.library.models to javafx.base;
        opens taas_tech.librarymanagementsystem.library.guiController to javafx.fxml;
        }
