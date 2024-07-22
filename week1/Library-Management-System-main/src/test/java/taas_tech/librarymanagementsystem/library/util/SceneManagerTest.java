////package taas_tech.librarymanagementsystem.library.util;
////
////import javafx.fxml.FXMLLoader;
////import javafx.scene.Parent;
////import javafx.scene.Scene;
////import javafx.stage.Stage;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////
////import java.io.IOException;
////
////import static org.mockito.ArgumentMatchers.any;
////import static org.mockito.Mockito.*;
////
////@ExtendWith(MockitoExtension.class)
////public class SceneManagerTest {
////
////    @Mock
////    private Stage stage;
////
////    @Mock
////    private FXMLLoader fxmlLoader;
////
////    @InjectMocks
////    private SceneManager sceneManager;
////
////    @Test
////    void loadScene_ShouldLoadFXMLAndShowStage() throws IOException {
////        // Arrange
////        Parent root = mock(Parent.class);
////        when(fxmlLoader.load(any())).thenReturn(root);
////        when(fxmlLoader.getController()).thenReturn(mock(Object.class));
////
////        // Act
////        SceneManager.loadScene(stage, "/path/to/fxml");
////
////        // Assert
////        verify(stage).setScene(any(Scene.class));
////        verify(stage).show();
////    }
////}
//
//
//package taas_tech.librarymanagementsystem.library.util;
//
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class SceneManagerTest extends ApplicationTest {
//
//    private Stage stage;
//
//    @Override
//    public void start(Stage stage) {
//        this.stage = stage;
//    }
//
//    @Test
//    public void testLoadScene() {
//        assertDoesNotThrow(() -> {
//            SceneManager.loadScene(stage, "/sample.fxml");
//        });
//
//        assertNotNull(stage.getScene());
//    }
//
//}