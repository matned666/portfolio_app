package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNumericValidator.class)
@Documented
public @interface IsNumeric {
    String message() default "It should be a numeric value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
