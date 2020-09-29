package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsCorrectPriceValidator implements ConstraintValidator<IsCorrectPrice, String> {

    @Override
    public void initialize(IsCorrectPrice constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            if (value != null) {
                if (value.trim().equals("")){
                    double val = Double.parseDouble(value);
                }
            }
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
}
