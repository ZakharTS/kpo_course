package com.course.project_javafx;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.ResultSet;

/*Управление учетными записями пользователей: просмотреть все учетные записи;
добавить учетную запись; отредактировать учетную запись; удалить учетную запись. */
public class AccountManagerController {

    public TableView<User> table;
    private final ObservableList<User> data = FXCollections.observableArrayList();
    public Label addLabel;
    public TextField loginField;
    public TextField passwordField;
    public ChoiceBox roleChoiceBox;
    public TextField editLoginField;
    public TextField editPasswordField;
    public ChoiceBox editRoleChoiceBox;
    public Label editLabel;


    public void updateTable() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            ResultSet rs = Database.sqlRequest("SELECT * FROM users;");
            data.clear();
            while (rs.next()) {
                User curUser = new User(rs.getString("login"), rs.getString("password"),
                        rs.getString("role"));
                data.add(curUser);
            }
            table.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onBackButtonClick() throws IOException {
        MainApplication.changeScene("menu-view.fxml", "Меню", 320, 240);
    }


    public void onAddButtonClick() {
        if (loginField.getText().equals("") || passwordField.getText().equals("")) {
            addLabel.setText("Заполните все поля");
            return;
        }
        try {
            ResultSet rs = Database.sqlRequest("SELECT * FROM users WHERE login=\"" + loginField.getText() + "\";");
            if (!rs.next()) {
                String uRole;
                if (roleChoiceBox.getValue().equals("Пользователь")) {
                    uRole = "0";
                } else if (roleChoiceBox.getValue().equals("Администратор")) {
                    uRole = "1";
                } else {
                    addLabel.setText("Что-то пошло не так.");
                    return;
                }
                Database.sqlUpdate("INSERT INTO users values(\"" + loginField.getText() + "\", \""
                        + AuthenticationController.stringToHash(passwordField.getText()) + "\", " + uRole + ");");
                addLabel.setText("Пользователь добавлен успешно.");
                loginField.setText("");
                passwordField.setText("");
                roleChoiceBox.setValue("Пользователь");
                this.updateTable();
            } else {
                addLabel.setText("Пользователь с таким логином уже существует.");
            }
        } catch (Exception ex) {
            addLabel.setText("Что-то пошло не так.");
            System.out.println(ex);
        }
    }

    public void onTableClicked(MouseEvent mouseEvent) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user == null) return;
//            System.out.println(user.getLogin() + " " + user.getPassword() + " " + user.getRole());
        editLoginField.setText(user.getLogin());
        editLabel.setText("");
        if (user.isRole()) {
            editRoleChoiceBox.setValue("Администратор");
        } else {
            editRoleChoiceBox.setValue("Пользователь");
        }
    }

    public void onDeleteButtonClicked() {
        if (true && table.getSelectionModel().getSelectedItem() != null) { // добавить подтверждение операции вместо true
            try {
                Database.sqlUpdate("DELETE FROM users WHERE login=\"" + table.getSelectionModel().getSelectedItem().getLogin() + "\";");
                this.updateTable();
                editLabel.setText("Запись удалена.");
            } catch (Exception e) {
                editLabel.setText("Что-то пошло не так.");
                System.out.println(e);
            }
        }
    }

    public void onEditButtonClicked() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            editLabel.setText("Выберите запись.");
            return;
        }
        if (editLoginField.getText().equals("") || editRoleChoiceBox.getValue().equals("")) {
            editLabel.setText("Недостаточно данных.");
            return;
        }
        if (true) { // добавить подтверждение операции вместо true
            String role;
            if (editRoleChoiceBox.getValue().equals("Администратор")) {
                role = "1";
            } else {
                role = "0";
            }
            String newLogin = editLoginField.getText();
            if (!newLogin.equals(table.getSelectionModel().getSelectedItem().getLogin())) {
                try {
                    ResultSet rs = Database.sqlRequest("SELECT * FROM users WHERE login=\"" + newLogin + "\";");
                    if (rs.next()) {
                        editLabel.setText("Пользователь с таким\nлогином уже существует.");
                        return;
                    }
                } catch (Exception e) {
                    editLabel.setText("Что-то пошло не так.");
                    System.out.println(e);
                    return;
                }
            }

            String newPassword = new String();
            if (!editPasswordField.equals("")) {
                try {
                    newPassword = AuthenticationController.stringToHash(editPasswordField.getText());
                } catch (Exception e) {}
            } else {
                newPassword = table.getSelectionModel().getSelectedItem().getPassword();
            }
            try {
                Database.sqlUpdate("UPDATE users SET login=\"" + newLogin + "\", password=\"" + newPassword
                         + "\", role=" + role +
                        " WHERE login=\"" + table.getSelectionModel().getSelectedItem().getLogin() + "\";");
                editLabel.setText("Запись обновлена.");
                editLoginField.setText("");
                editPasswordField.setText("");
                editRoleChoiceBox.setValue("            ");
                this.updateTable();
            } catch (Exception e) {
                editLabel.setText("Что-то пошло не так.");
                System.out.println(e);;
            }
        }
    }
}
