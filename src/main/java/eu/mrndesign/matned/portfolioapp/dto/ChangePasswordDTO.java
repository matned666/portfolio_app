package eu.mrndesign.matned.portfolioapp.dto;


import eu.mrndesign.matned.portfolioapp.validation.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class ChangePasswordDTO {

    private String login;

    @NotNull(message = "The password field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String password;

    @NotNull(message = "This field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String passwordConfirm;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String login, String password, String passwordConfirm) {
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
