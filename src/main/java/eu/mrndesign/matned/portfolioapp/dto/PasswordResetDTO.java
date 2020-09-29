package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.PasswordReset;
import eu.mrndesign.matned.portfolioapp.validation.EmailExist;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordResetDTO {

    @NotNull(message = "The login cannot be empty")
    @Size(min = 5, message = "The login must be at least {min} signs long")
    @Pattern(
            regexp = ".{1,}@.{1,}[.].{2,3}",
            message = "It should be a valid email address"
    )
    @EmailExist
    private String login;

    public PasswordResetDTO() {
    }

    public static PasswordResetDTO apply(PasswordReset byToken) {

        return null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
