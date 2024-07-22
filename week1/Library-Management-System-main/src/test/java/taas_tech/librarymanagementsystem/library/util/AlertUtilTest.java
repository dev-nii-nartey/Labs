package taas_tech.librarymanagementsystem.library.util;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class AlertUtilTest {

    @Start
    public void start(Stage stage) {
        // This method is required to initialize the JavaFX toolkit
    }

    @Test
    public void testShowError() throws Exception {
        FxToolkit.setupFixture(() -> {
            AlertUtil.showError("Error Title", "Error Content");
        });

        // Asserts or verification logic can be added here
        // For simplicity, the current test just ensures no exception is thrown
    }

    @Test
    public void testShowInfo() throws Exception {
        FxToolkit.setupFixture(() -> {
            AlertUtil.showInfo("Info Title", "Info Content");
        });

        // Asserts or verification logic can be added here
        // For simplicity, the current test just ensures no exception is thrown
    }
}
