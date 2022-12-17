package com.course.project_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class AuthenticationController {
    @FXML
    public Label errorText;
    public TextField loginText;
    public PasswordField passwordText;

    @FXML
    public void onLoginButtonClick() {
        try {
            String login = loginText.getText();
            String password = passwordText.getText();
            MainApplication.setUser(auth(login, password));
            if (MainApplication.getUser() != null) {
                MainApplication.changeScene("menu-view.fxml", "Меню", 320, 240);
            } else {
                errorText.setText("Неверный логин или пароль");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static User auth(String login, String password) throws NoSuchAlgorithmException { // авторизация
        User user = null;

        String passwordHash = stringToHash(password);
//        System.out.println(login);
//        System.out.println(passwordHash);

        // запрос правильного пароля из базы
        ResultSet rs = Database.sqlRequest("SELECT * FROM users WHERE login=\"" + login + "\";");
        try {
            rs.next();
//            System.out.println(rs.getString("password"));
            if (passwordHash.equals(rs.getString("password"))) {
                user = new User(rs.getString("login"), rs.getString("role"));
                System.out.println("User " + login + " logged in.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public static String stringToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // хеширование пароля
        byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, hash));
    }
}