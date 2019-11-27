package softuni.residentevil.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateBeforeTodayValidator implements ConstraintValidator<BeforeToday, Date> {
   public void initialize(BeforeToday constraint) {
   }

   public boolean isValid(Date date, ConstraintValidatorContext context) {
      return date != null && date.before(new Date());
   }
}
