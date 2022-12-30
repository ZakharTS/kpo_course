package com.course.project_javafx;

public class User {
    private String login;
    private String password;
    private boolean role;

    User(String login, boolean role) {
        this.login = login;
        this.password = null;
        this.role = role;
    }

    User(String login, String password, boolean role) {
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
        if (role) {
            return "1";
        } else {
            return "0";
        }
    }

    public boolean isRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
