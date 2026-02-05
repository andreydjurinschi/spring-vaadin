package org.cedacri.batch.vaadintutorial.core.models.dto;

public class RegisterForm {

    private String login;
    private String fullName;
    private String email;
    private String password;
    private String repeatPassword;

    public RegisterForm(String login, String fullName, String email, String password, String repeatPassword) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public RegisterForm() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}

