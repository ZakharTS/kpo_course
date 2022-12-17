package com.course.project_javafx;

public class User {
    private String login;
    private String password;
    private String role;

    User(String login, String role) {
        this.login = login;
        this.password = null;
        this.role = role;
    }

    User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public boolean isRole() {
        if(role.equals("1")) return true;
        else return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
