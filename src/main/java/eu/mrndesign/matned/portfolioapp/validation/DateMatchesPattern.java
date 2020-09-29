package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateMatchesPatternValidator.class)
@Documented
public @interface DateMatchesPattern {

    String message() default "Wrong date format - should be yyyy-MM-dd";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
