package eu.mrndesign.matned.portfolioapp.validation;

import eu.mrndesign.matned.portfolioapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.existsByLogin(value);
    }
}
