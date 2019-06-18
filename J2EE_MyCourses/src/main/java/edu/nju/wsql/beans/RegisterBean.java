package edu.nju.wsql.beans;

public class RegisterBean {
    private String password;
    private String confirmPassword;
    private String name;
    private String email;
    private boolean isVue;

    public RegisterBean() {
        password = "";
        confirmPassword = "";
        name = "";
        email = "";
        isVue = false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVue() {
        return isVue;
    }

    public void setVue(boolean vue) {
        isVue = vue;
    }
}
