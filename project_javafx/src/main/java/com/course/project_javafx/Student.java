package com.course.project_javafx;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    //    private String id;
//    private String NSP; // ФИО
//    private String group; // группа
//    private String eduForm; // форма обучения
//    private String[] credits; // зачёты ??
//    private String[] exams; // экзамены
//    private String socWork; // участие в общественной работе
//    private String scholarship;
    private int id;
    private String NSP; // ФИО
    private String group; // группа
    private boolean eduForm; // форма обучения
    private boolean[] credits; // зачёты ??
    private int[] exams; // экзамены
    private boolean socWork; // участие в общественной работе
    private double scholarship; // размер стипендии

    Student(int id, String group, String NSP, boolean eduForm, boolean[] credits, int[] exams,
            boolean socWork, double scholarship) {
        this.id = id;
        this.group = group;
        this.NSP = NSP;
        this.eduForm = eduForm;
        this.credits = new boolean[5];
        for (int i = 0; i < 5; i++) {
            this.credits[i] = credits[i];
        }
        this.exams = new int[4];
        for (int i = 0; i < 4; i++) {
            this.exams[i] = exams[i];
        }
        this.socWork = socWork;
        this.scholarship = scholarship;
    }

    Student(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        group = rs.getString("grp");
        NSP = rs.getString("nsp");
        eduForm = rs.getBoolean("eduForm");
        credits = new boolean[5];
        String[] words = rs.getString("credits").split(", ");
        for (int i = 0; i < 5; i++) {
            if (words[i].equals("1")) {
                credits[i] = true;
            } else {
                credits[i] = false;
            }
        }
        exams = new int[4];
        words = rs.getString("exams").split(", ");
        for (int i = 0; i < 4; i++) {
            exams[i] = Integer.parseInt(words[i]);
        }
        socWork = rs.getBoolean("socWork");
        scholarship = rs.getDouble("scholarship");
    }

    public String getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNSP() {
        return NSP;
    }

    public void setNSP(String NSP) {
        this.NSP = NSP;
    }

    public String getEduForm() {
        if (eduForm) {
            return "Б";
        } else {
            return "П";
        }
    }
    public boolean getEduFormRaw() {
        return eduForm;
    }

    public void setEduForm(boolean eduForm) {
        this.eduForm = eduForm;
    }

    public String getCredit0() {
        if (credits[0]) {
            return "Зачт.";
        } else {
            return "Н/З";
        }
    }

    public String getCredit1() {
        if (credits[1]) {
            return "Зачт.";
        } else {
            return "Н/З";
        }
    }

    public String getCredit2() {
        if (credits[2]) {
            return "Зачт.";
        } else {
            return "Н/З";
        }
    }

    public String getCredit3() {
        if (credits[3]) {
            return "Зачт.";
        } else {
            return "Н/З";
        }
    }

    public String getCredit4() {
        if (credits[4]) {
            return "Зачт.";
        } else {
            return "Н/З";
        }
    }

    public void setCredits(boolean[] credits) {
        this.credits = credits;
    }

    public int[] getExams() {
        return exams;
    }

    public String getExam0() {
        return Integer.toString(exams[0]);
    }

    public String getExam1() {
        return Integer.toString(exams[1]);
    }

    public String getExam2() {
        return Integer.toString(exams[2]);
    }

    public String getExam3() {
        return Integer.toString(exams[3]);
    }

    public void setExams(int[] exams) {
        this.exams = exams;
    }

    public String getSocWork() {
        if (socWork) {
            return "Акт.";
        } else {
            return "Неакт.";
        }
    }
    public boolean getSocWorkRaw() {
        return socWork;
    }

    public void setSocWork(boolean socWork) {
        this.socWork = socWork;
    }

    public boolean[] getCredits() {
        return credits;
    }

    public String getScholarship() {
        return Double.toString(scholarship);
    }

    public void setScholarship(double scholarship) {
        this.scholarship = scholarship;
    }

    public String toString() {
        return id + " " + NSP + " " + group + " " + eduForm + " " + socWork;
    }
}
