package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class IsNumericBigDecimalValidator implements ConstraintValidator<IsNumericBigDecimal, BigDecimal> {

    @Override
    public void initialize(IsNumericBigDecimal constraintAnnotation) {

    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        try{
            BigDecimal b = new BigDecimal(0).add(value);
            return true;
        }catch (Exception ignored){
            return false;
        }
    }
}
