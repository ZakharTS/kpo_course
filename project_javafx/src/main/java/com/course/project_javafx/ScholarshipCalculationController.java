package com.course.project_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.ResultSet;

public class ScholarshipCalculationController {

    public TableView<Student> table;
    private final ObservableList<Student> data = FXCollections.observableArrayList();
    public ChoiceBox searchChoiceBox;
    public TextField searchTextField;
    public Label errorLabel;
    public TextField scholarshipTextField;

    public void updateTable() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            ResultSet rs = Database.sqlRequest("SELECT * FROM students;");
            data.clear();
            while (rs.next()) {
                data.add(new Student(rs));
            }
            table.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onBackButtonClicked() throws IOException {
        MainApplication.changeScene("menu-view.fxml", "Меню", 320, 240);
    }

    public void onTableClicked(MouseEvent mouseEvent) {
    }

    public void onSearchButtonClicked() {
        updateTable();
        ObservableList<Student> newData = FXCollections.observableArrayList();
        Object value = searchChoiceBox.getValue();
        for (Student curStudent : data) {
            if (value.equals("по всем полям")) {
                if (curStudent.toString().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            } else if (value.equals("id")) {
                if (curStudent.getId().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            } else if (value.equals("ФИО")) {
                if (curStudent.getNSP().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            } else if (value.equals("группа")) {
                if (curStudent.getGroup().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            } else if (value.equals("форма")) {
                if (curStudent.getEduForm().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            } else if (value.equals("общ. деятельность")) {
                if (curStudent.getSocWork().contains(searchTextField.getText())) {
                    newData.add(curStudent);
                }
            }
        }
        table.setItems(newData);
    }

    public void onCalculateButtonClick() {
        if (scholarshipTextField.getText().equals("")) return;
        if (Double.parseDouble(scholarshipTextField.getText()) < 0) return;
        for (Student cur : data) {
            if (cur.getEduFormRaw()) {
                int sum = 0;
                boolean isExc = true;
                for (int exam : cur.getExams()) {
                    if (exam < 9) {
                        isExc = false;
                    }
                    sum += exam;
                }
                if (sum / 4.0 < 5.0) {
                    cur.setScholarship(0.0);
                    continue;
                }

                double k = 1.0;
                if (isExc) {
                    k += 0.25;
                    if (cur.getSocWorkRaw()) {
                        k += 0.25;
                    }
                }
                for (boolean credit : cur.getCredits()) {
                    if (!credit) {
                        k = 0;
                        break;
                    }
                }
                cur.setScholarship(Double.parseDouble(scholarshipTextField.getText()) * k);

            }
            Database.sqlUpdate("UPDATE students SET scholarship=" + cur.getScholarship() + " WHERE id=" + cur.getId());
        }
        updateTable();
    }

    public void onAdminButtonClicked() throws IOException {
        if (MainApplication.getUser().isRole()) {
            MainApplication.changeScene("scholarship-admin-view.fxml", "Расчёт стипендии", 1280, 720);
        } else {
            errorLabel.setText("Недостаточно прав.");
        }
    }
}
