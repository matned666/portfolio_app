package eu.mrndesign.matned.portfolioapp.dto;


import eu.mrndesign.matned.portfolioapp.model.PasswordReset;
import eu.mrndesign.matned.portfolioapp.validation.PasswordResetMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordResetMatches
public class PasswordDTO {

    private String userName;

    @NotNull(message = "The password field cannot be empty")
    @NotEmpty(message = "The password field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String password;

    @NotNull(message = "The field cannot be empty")
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 3, message = "The password must be at least {min} signs long")
    private String passwordConfirm;

    public PasswordDTO(String userName) {
        this.userName = userName;
    }

    public static PasswordDTO apply(PasswordReset byToken) {
        if (byToken != null) {
            return new PasswordDTO(byToken.getUser().getLogin());
        }
        else return null;
    }

    public String getUserName() {
        return userName;
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
