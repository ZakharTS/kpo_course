package com.course.project_javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/student";
    static final String USER = "root";
    static final String PASS = "toor";

    public static ResultSet sqlRequest(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static void sqlUpdate(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
