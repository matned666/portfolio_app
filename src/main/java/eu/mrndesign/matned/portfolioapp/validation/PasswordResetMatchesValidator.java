package eu.mrndesign.matned.portfolioapp.validation;


import eu.mrndesign.matned.portfolioapp.dto.PasswordDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordResetMatchesValidator implements ConstraintValidator<PasswordResetMatches, Object> {

    @Override
    public void initialize(final PasswordResetMatches constraintAnnotation) {

    }


    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final PasswordDTO password = (PasswordDTO) obj;
        return password.getPassword().equals(password.getPasswordConfirm());
    }

}
