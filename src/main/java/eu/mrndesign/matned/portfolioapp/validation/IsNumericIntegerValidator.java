package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class IsNumericIntegerValidator implements ConstraintValidator<IsNumericInteger, Integer> {

    @Override
    public void initialize(IsNumericInteger constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        try{
            Integer b = value;
            return true;
        }catch (Exception ignored){
            return false;
        }
    }
}
