package com.course.project_javafx;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String id;
    private String NSP; // ФИО
    private String group; // группа
    private String eduForm; // форма обучения
    private String[] credits; // зачёты ??
    private String[] exams; // экзамены
    private String socWork; // участие в общественной работе
    private String scholarship;

    Student(int id, String group, String NSP, boolean eduForm, boolean[] credits, int[] exams,
            boolean socWork, double scholarship) {
        this.id = Integer.toString(id);
        this.group = group;
        this.NSP = NSP;
        if (eduForm) {
            this.eduForm = "Б";
        } else {
            this.eduForm = "П";
        }
        this.credits = new String[5];
        for (int i = 0; i < 5; i++) {
            if (credits[i]) {
                this.credits[i] = "Зачт.";
            } else {
                this.credits[i] = "Н/З";
            }
        }
        this.exams = new String[4];
        for (int i = 0; i < 4; i++) {
            this.exams[i] = Integer.toString(exams[i]);
        }
        if (socWork) {
            this.socWork = "Акт.";
        } else {
            this.socWork = "Неакт.";
        }
        this.scholarship = Double.toString(scholarship);
    }

    Student(ResultSet rs) throws SQLException {
        id = rs.getString("id");
        group = rs.getString("grp");
        NSP = rs.getString("nsp");
        if (rs.getBoolean("eduForm")) {
            eduForm = "Б";
        } else {
            eduForm = "П";
        }
        credits = new String[5];
        String[] words = rs.getString("credits").split(", ");
        for (int i = 0; i < 5; i++) {
            if (words[i].equals("1")) {
                credits[i] = "Зачт.";
            } else {
                credits[i] = "Н/З";
            }
        }
        exams = new String[4];
        words = rs.getString("exams").split(", ");
        for (int i = 0; i < 5; i++) {
            exams[i] = words[i];
        }
        if (rs.getBoolean("socWork")) {
            socWork = "Акт.";
        } else {
            socWork = "Неакт.";
        }
        scholarship = rs.getString("scholarship");
    }

        public String getId () {
            return id;
        }

        public void setId (String id){
            this.id = id;
        }

        public String getGroup () {
            return group;
        }

        public void setGroup (String group){
            this.group = group;
        }

        public String getNSP () {
            return NSP;
        }

        public void setNSP (String NSP){
            this.NSP = NSP;
        }

        public String getEduForm () {
            return eduForm;
        }

        public void setEduForm (String eduForm){
            this.eduForm = eduForm;
        }

        public String getCredit0 () {
            return credits[0];
        }
        public String getCredit1 () {
            return credits[1];
        }
        public String getCredit2 () {
            return credits[2];
        }
        public String getCredit3 () {
            return credits[3];
        }
        public String getCredit4 () {
            return credits[4];
        }

        public void setCredits (String[]credits){
            this.credits = credits;
        }

        public String[] getExams () {
            return exams;
        }

        public String getExam0 () {
            return exams[0];
        }

        public String getExam1 () {
            return exams[1];
        }
        public String getExam2 () {
            return exams[2];
        }
        public String getExam3 () {
            return exams[3];
        }
        public void setExams (String[]exams){
            this.exams = exams;
        }

        public String getSocWork () {
            return socWork;
        }

        public void setSocWork (String socWork){
            this.socWork = socWork;
        }

        public String[] getCredits () {
            return credits;
        }

        public String getScholarship () {
            return scholarship;
        }

        public void setScholarship (String scholarship){
            this.scholarship = scholarship;
        }

        public String toString () {
            return id + " " + NSP + " " + group + " " + eduForm + " " + socWork;
        }
    }
