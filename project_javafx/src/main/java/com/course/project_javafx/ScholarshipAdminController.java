package com.course.project_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.ResultSet;

public class ScholarshipAdminController {

    public TableView<Student> table;
    private final ObservableList<Student> data = FXCollections.observableArrayList();
    public ChoiceBox searchChoiceBox;
    public TextField searchTextField;

    public void updateTable(String query) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        data.add(new Student(0, "ПО-9", "Харитонович З. С.", true,
//                new boolean[]{true, true, true, true, true}, new int[]{10, 10, 10, 10}, true, 120.0));
        try {
            ResultSet rs = Database.sqlRequest(query);
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
        MainApplication.changeScene("scholarship-calculation-view.fxml", "Расчёт стипендии", 1280, 720);
    }

    public void onAddButtonClicked(ActionEvent actionEvent) {

    }

    public void onEditButtonClicked(ActionEvent actionEvent) {
    }

    public void onTableClicked(MouseEvent mouseEvent) {
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
    }

    public void onSearchButtonClicked(ActionEvent actionEvent) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        updateTable("SELECT * FROM students;");
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
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        updateTable("SELECT * FROM students;");
    }

    public void onAdminButtonClicked(ActionEvent actionEvent) {
    }
}
