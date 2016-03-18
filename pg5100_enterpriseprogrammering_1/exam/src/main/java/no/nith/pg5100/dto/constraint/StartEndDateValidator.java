package no.nith.pg5100.dto.constraint;

import no.nith.pg5100.dto.Event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartEndDateValidator implements ConstraintValidator<ValidStartEndDate, Event> {

    @Override
    public void initialize(ValidStartEndDate validStartEndDate) {

    }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext constraintValidatorContext) {
        if (event.getStartDate() == null || event.getEndDate() == null) return false;
        return !event.getStartDate().after(event.getEndDate());
    }
}
