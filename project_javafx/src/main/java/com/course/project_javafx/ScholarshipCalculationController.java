package com.course.project_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
//        data.add(new Student(0, "ПО-9", "Харитонович З. С.", true,
//                new boolean[]{true, true, true, true, true}, new int[]{10, 10, 10, 10}, true, 120.0));
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

    public void onSearchButtonClicked(ActionEvent actionEvent) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        updateTable();
        ObservableList<Student> newData = FXCollections.observableArrayList();
//        data.add(new Student(0, "ПО-9", "Харитонович З. С.", true,
//                new boolean[]{true, true, true, true, true}, new int[]{10, 10, 10, 10}, true, 120.0));
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

    public void onCalculateButtonClick(ActionEvent actionEvent) {
        updateTable();
        ObservableList<Student> newData = FXCollections.observableArrayList();
        for (Student cur : data) {
            if (cur.getEduForm().equals("Б")) {
                for (String credit : cur.getCredits()) {
                    if (credit.equals("Н/З")) {
                        cur.setScholarship("0.0");
                        break;
                    }
                }
                int sum = 0;
                boolean isK1 = true;
                double k = 1.5;
                for (String exam : cur.getExams()) {
                    if (Integer.parseInt(exam) < 9) {
                        isK1 = false;
                    }
                    sum += Integer.parseInt(exam);
                }
                if (sum / 4.0 < 5.0) {
                    cur.setScholarship("0.0");
                    continue;
                }
                if (!isK1) {
                    k -= 0.25;
                }
                if (cur.getSocWork().equals("Неакт.")) {
                    k -= 0.25;
                }
                cur.setScholarship(Double.toString(Double.parseDouble(scholarshipTextField.getText()) * k));
            }
            newData.add(cur);
        }
        table.setItems(newData);
    }

    public void onAdminButtonClicked(ActionEvent actionEvent) throws IOException {
        if (MainApplication.getUser().isRole()) {
            MainApplication.changeScene("scholarship-admin-view.fxml", "Расчёт стипендии", 1280, 720);
        } else {
            errorLabel.setText("Недостаточно прав.");
        }
    }
}
