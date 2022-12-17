package com.course.project_javafx;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class MenuController {
    public Label errorText;

    public void onAccountManagerButtonClick() throws IOException {
        if (MainApplication.getUser().isRole())
            MainApplication.changeScene("account-manager-view.fxml", "Управление учётными записями", 1280, 720);
        else errorText.setText("Недостаточно прав.");
    }

    public void onScholarshipCalculationButtonClick() throws IOException {
        MainApplication.changeScene("scholarship-calculation-view.fxml", "Расчёт стипендии", 320, 240);
    }

    public void onExitButtonClick() throws IOException {
        MainApplication.setUser(null);
        MainApplication.changeScene("authentication-view.fxml", "Авторизация", 320, 240);
    }
}
