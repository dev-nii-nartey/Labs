package taas_tech.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        DatabaseHelper.createTables();
        primaryStage.setTitle("Library Management System");
        showSignupScreen();  // Start with the signup screen
    }

    public static void showSignupScreen() throws IOException {
        setFixedSize(800, 500);  // Set the size for login/signup screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showLoginScreen() throws IOException {
        setFixedSize(800, 500);  // Set the size for login/signup screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showAddBookScreen() throws IOException {
        setFixedSize(800, 600);  // Set the size for add book/register patron screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add_book.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showIssueBookScreen() throws IOException {
        setFixedSize(600, 500);  // Set the size for issue book/return book screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("issue_book.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showRegisterPatronScreen() throws IOException {
        setFixedSize(600, 500);  // Set the size for add book/register patron screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register_patron.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showReturnBookScreen() throws IOException {
        setFixedSize(600, 500);  // Set the size for issue book/return book screens
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("return_book.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void setFixedSize(int width, int height) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
