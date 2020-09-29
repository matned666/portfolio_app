package eu.mrndesign.matned.portfolioapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistValidator.class)
@Documented
public @interface EmailExist {

    String message() default "A user with the login doesn't exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
