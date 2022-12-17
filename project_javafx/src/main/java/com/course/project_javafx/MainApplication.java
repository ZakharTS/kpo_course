package com.course.project_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage guiStage;
    private static User user;

    public static Stage getStage() {
        return guiStage;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainApplication.user = user;
    }

    @Override
    public void start(Stage stage) throws IOException {
        guiStage = stage;
        changeScene("authentication-view.fxml", "Авторизация", 320, 240);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String file, String title, int v, int v1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(file));
        Scene authScene = new Scene(fxmlLoader.load(), v, v1);
        getStage().setTitle(title);
        getStage().setScene(authScene);
    }
}