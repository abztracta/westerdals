package no.nith.pg5100.dto.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StartEndDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStartEndDate {
    String message() default "Invalid start or end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
