package com.course.project_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class ScholarshipAdminController {

    public TableView<Student> table;
    private final ObservableList<Student> data = FXCollections.observableArrayList();
    public ChoiceBox searchChoiceBox;
    public TextField searchTextField;
    public TextField editNSPField;
    public Label editLabel;
    public TextField editGroupField;
    public ChoiceBox editEduFormChoiceBox;
    public CheckBox credit0CheckBox;
    public CheckBox credit1CheckBox;
    public CheckBox credit2CheckBox;
    public CheckBox credit3CheckBox;
    public CheckBox credit4CheckBox;
    public ChoiceBox exam0ChoiceBox;
    public ChoiceBox exam1ChoiceBox;
    public ChoiceBox exam2ChoiceBox;
    public ChoiceBox exam3ChoiceBox;
    public ChoiceBox editSocWorkChoiceBox;
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
        MainApplication.changeScene("scholarship-calculation-view.fxml", "Расчёт стипендии", 1280, 720);
    }

    public void onAddButtonClicked(ActionEvent actionEvent) {
        String NSP = editNSPField.getText();
        String group = editGroupField.getText();

        String socWork = new String();
        if (editSocWorkChoiceBox.getValue().toString().equals("Активная")) {
            socWork = "1";
        } else if (editSocWorkChoiceBox.getValue().toString().equals("Неактивная")) {
            socWork = "0";
        }
        String eduForm = new String();
        if (editEduFormChoiceBox.getValue().toString().equals("Бюджетная")) {
            eduForm = "1";
        } else if (editEduFormChoiceBox.getValue().toString().equals("Платная")) {
            eduForm = "0";
        }
        if (NSP.equals("") || group.equals("") || socWork.equals("") || eduForm.equals("") ||
                exam0ChoiceBox.getValue().equals("") || exam1ChoiceBox.getValue().equals("") ||
                exam2ChoiceBox.getValue().equals("") || exam3ChoiceBox.getValue().equals("")) {
            editLabel.setText("Заполните все поля.");
            return;
        }
        ArrayList<CheckBox> creditsCheckBoxes = new ArrayList<>() {
            {
                add(credit0CheckBox);
                add(credit1CheckBox);
                add(credit2CheckBox);
                add(credit3CheckBox);
                add(credit4CheckBox);
            }
        };
        String credits = new String();
        for (CheckBox cur : creditsCheckBoxes) {
            if (cur.isSelected()) {
                credits += "1, ";
            } else {
                credits += "0, ";
            }
        }
        credits = credits.substring(0, credits.length() - 2);
        ArrayList<ChoiceBox> examsChoiceBoxes = new ArrayList<>() {
            {
                add(exam0ChoiceBox);
                add(exam1ChoiceBox);
                add(exam2ChoiceBox);
                add(exam3ChoiceBox);
            }
        };
        String exams = new String();
        for (ChoiceBox cur : examsChoiceBoxes) {
            exams += cur.getValue().toString() + ", ";
        }
        exams = exams.substring(0, exams.length() - 2);

        try {
            Database.sqlUpdate("INSERT INTO students values (NULL, \"" + NSP + "\", \"" + group + "\", " +
                    eduForm + ", \"" + credits + "\", \"" + exams + "\", " + socWork + ", 0);");
            updateTable();
        } catch (Exception e) {
            System.out.println(e);
            editLabel.setText("Что-то пошло не так.");
        }
    }

    public void onEditButtonClicked(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            editLabel.setText("Выберите запись.");
            return;
        }
        String NSP = editNSPField.getText();
        String group = editGroupField.getText();

        String socWork = new String();
        if (editSocWorkChoiceBox.getValue().toString().equals("Активная")) {
            socWork = "1";
        } else if (editSocWorkChoiceBox.getValue().toString().equals("Неактивная")) {
            socWork = "0";
        }
        String eduForm = new String();
        if (editEduFormChoiceBox.getValue().toString().equals("Бюджетная")) {
            eduForm = "1";
        } else if (editEduFormChoiceBox.getValue().toString().equals("Платная")) {
            eduForm = "0";
        }
        if (NSP.equals("") || group.equals("") || socWork.equals("") || eduForm.equals("") ||
                exam0ChoiceBox.getValue().equals("") || exam1ChoiceBox.getValue().equals("") ||
                exam2ChoiceBox.getValue().equals("") || exam3ChoiceBox.getValue().equals("")) {
            editLabel.setText("Заполните все поля.");
            return;
        }
        ArrayList<CheckBox> creditsCheckBoxes = new ArrayList<>() {
            {
                add(credit0CheckBox);
                add(credit1CheckBox);
                add(credit2CheckBox);
                add(credit3CheckBox);
                add(credit4CheckBox);
            }
        };
        String credits = new String();
        for (CheckBox cur : creditsCheckBoxes) {
            if (cur.isSelected()) {
                credits += "1, ";
            } else {
                credits += "0, ";
            }
        }
        credits = credits.substring(0, credits.length() - 2);
        ArrayList<ChoiceBox> examsChoiceBoxes = new ArrayList<>() {
            {
                add(exam0ChoiceBox);
                add(exam1ChoiceBox);
                add(exam2ChoiceBox);
                add(exam3ChoiceBox);
            }
        };
        String exams = new String();
        for (ChoiceBox cur : examsChoiceBoxes) {
            exams += cur.getValue().toString() + ", ";
        }
        exams = exams.substring(0, exams.length() - 2);

        try {
            Database.sqlUpdate("UPDATE students SET nsp=\"" + NSP + "\", grp=\"" + group + "\", eduForm="
                    + eduForm + ", credits=\"" + credits + "\", exams=\"" + exams + "\", socWork=" + socWork +
                    " WHERE id=" + table.getSelectionModel().getSelectedItem().getId() + ";");
            updateTable();
            editLabel.setText("");
            editNSPField.setText("");
            editGroupField.setText("");
            editSocWorkChoiceBox.setValue("");
            editEduFormChoiceBox.setValue("");
            credit0CheckBox.setSelected(false);
            credit1CheckBox.setSelected(false);
            credit2CheckBox.setSelected(false);
            credit3CheckBox.setSelected(false);
            credit4CheckBox.setSelected(false);
            exam0ChoiceBox.setValue("");
            exam1ChoiceBox.setValue("");
            exam2ChoiceBox.setValue("");
            exam3ChoiceBox.setValue("");
        } catch (Exception e) {
            System.out.println(e);
            editLabel.setText("Что-то пошло не так.");
        }
    }

    public void onTableClicked(MouseEvent mouseEvent) {
        Student student = table.getSelectionModel().getSelectedItem();
        if (student == null) return;
        editNSPField.setText(student.getNSP());
        editGroupField.setText(student.getGroup());

        if (student.getSocWork().equals("1")) {
            editSocWorkChoiceBox.setValue("Активная");
        } else if (student.getSocWork().equals("0")) {
            editSocWorkChoiceBox.setValue("Неактивная");
        }

        if (student.getEduForm().equals("Б")) {
            editEduFormChoiceBox.setValue("Бюджетная");
        } else if (student.getEduForm().equals("П")) {
            editEduFormChoiceBox.setValue("Платная");
        }
        if (student.getCredit0().equals("Зачт.")) {
            credit0CheckBox.setSelected(true);
        } else {
            credit0CheckBox.setSelected(false);
        }
        if (student.getCredit1().equals("Зачт.")) {
            credit1CheckBox.setSelected(true);
        } else {
            credit1CheckBox.setSelected(false);
        }
        if (student.getCredit2().equals("Зачт.")) {
            credit2CheckBox.setSelected(true);
        } else {
            credit2CheckBox.setSelected(false);
        }
        if (student.getCredit3().equals("Зачт.")) {
            credit3CheckBox.setSelected(true);
        } else {
            credit3CheckBox.setSelected(false);
        }
        if (student.getCredit4().equals("Зачт.")) {
            credit4CheckBox.setSelected(true);
        } else {
            credit4CheckBox.setSelected(false);
        }
        exam0ChoiceBox.setValue(student.getExam0());
        exam1ChoiceBox.setValue(student.getExam1());
        exam2ChoiceBox.setValue(student.getExam2());
        exam3ChoiceBox.setValue(student.getExam3());
        if (student.getSocWork().equals("Акт.")) {
            editSocWorkChoiceBox.setValue("Активная");
        } else {
            editSocWorkChoiceBox.setValue("Неактивная");
        }
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        if (true && table.getSelectionModel().getSelectedItem() != null) { // добавить подтверждение операции вместо true
            try {
                Database.sqlUpdate("DELETE FROM students WHERE id=\"" + table.getSelectionModel().getSelectedItem().getId() + "\";");
                this.updateTable();
                editLabel.setText("Запись удалена.");
            } catch (Exception e) {
                editLabel.setText("Что-то пошло не так.");
                System.out.println(e);
            }
        }
    }

    public void onSearchButtonClicked(ActionEvent actionEvent) {
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
}
