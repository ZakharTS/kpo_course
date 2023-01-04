package com.course.project_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ScholarshipAdminController extends ScholarshipCalculationController {

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

    @Override
    public void onBackButtonClicked() throws IOException {
        MainApplication.changeScene("scholarship-calculation-view.fxml", "Расчёт стипендии", 1280, 720);
    }

    public void onAddButtonClicked() {
        String[] fields = formToStrings();
        if (fields == null) {
            editLabel.setText("Проверьте поля.");
            return;
        }
        try {
            Database.sqlUpdate("INSERT INTO students values (NULL, \"" + fields[0] + "\", \"" + fields[1] + "\", " +
                    fields[2] + ", \"" + fields[3] + "\", \"" + fields[4] + "\", " + fields[5] + ", 0);");
            updateTable();
            clearForm();
        } catch (Exception e) {
            System.out.println(e);
            editLabel.setText("Что-то пошло не так.");
        }
    }

    public void onDeleteButtonClicked() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            editLabel.setText("Выберите запись.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Удаление записи студента " + table.getSelectionModel().getSelectedItem().getNSP());
        alert.setContentText("Вы точно хотите удалить эту запись?");
        ButtonType buttonYes = new ButtonType("Да");
        ButtonType buttonCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonYes) {
            try {
                Database.sqlUpdate("DELETE FROM students WHERE id=\"" + table.getSelectionModel().getSelectedItem().getId() + "\";");
                updateTable();
                editLabel.setText("Запись удалена.");
                clearForm();
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
        final String[] fields = formToStrings();
        if (fields == null) {
            editLabel.setText("Проверьте поля.");
            return;
        }
        try {
            Database.sqlUpdate("UPDATE students SET nsp=\"" + fields[0] + "\", grp=\"" + fields[1] + "\", eduForm="
                    + fields[2] + ", credits=\"" + fields[3] + "\", exams=\"" + fields[4] + "\", socWork=" + fields[5] +
                    " WHERE id=" + table.getSelectionModel().getSelectedItem().getId() + ";");
            updateTable();
            editLabel.setText("Запись изменена.");
            clearForm();
        } catch (Exception e) {
            System.out.println(e);
            editLabel.setText("Что-то пошло не так.");
        }
    }

    public void clearForm() {
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
    }

    private String[] formToStrings() {
        String NSP = editNSPField.getText();
        if (!NSP.matches("[А-Я][а-я]+\\s+[А-Я]\\.\\s+[А-Я]\\.")) {
            return null;
        }
        String group = editGroupField.getText();
        if (!group.matches("")) {
            return null;
        }
        String socWork = "";
        if (editSocWorkChoiceBox.getValue().toString().equals("Активная")) {
            socWork = "1";
        } else if (editSocWorkChoiceBox.getValue().toString().equals("Неактивная")) {
            socWork = "0";
        }
        String eduForm = "";
        if (editEduFormChoiceBox.getValue().toString().equals("Бюджетная")) {
            eduForm = "1";
        } else if (editEduFormChoiceBox.getValue().toString().equals("Платная")) {
            eduForm = "0";
        }
        if (NSP.equals("") || group.equals("") || socWork.equals("") || eduForm.equals("") ||
                exam0ChoiceBox.getValue() == null || exam1ChoiceBox.getValue() == null ||
                exam2ChoiceBox.getValue() == null || exam3ChoiceBox.getValue() == null) {
            editLabel.setText("Заполните все поля.");
            return null;
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
        String credits = "";
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
        String exams = "";
        for (ChoiceBox cur : examsChoiceBoxes) {
            exams += cur.getValue().toString() + ", ";
        }
        exams = exams.substring(0, exams.length() - 2);

        String[] strings = {NSP, group, eduForm, credits, exams, socWork};
        return strings;
    }

    @Override
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
        credit0CheckBox.setSelected(student.getCredit0().equals("Зачт."));
        credit1CheckBox.setSelected(student.getCredit1().equals("Зачт."));
        credit2CheckBox.setSelected(student.getCredit2().equals("Зачт."));
        credit3CheckBox.setSelected(student.getCredit3().equals("Зачт."));
        credit4CheckBox.setSelected(student.getCredit4().equals("Зачт."));
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
}
