package eu.mrndesign.matned.portfolioapp.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingDateFormat implements DateValidator {
    private final DateTimeFormatter dateFormatter;


    public DateValidatorUsingDateFormat(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        if(dateStr != null) {
            if (!dateStr.trim().equals("")) {
                try {
                    LocalDate.parse(dateStr, this.dateFormatter);
                } catch (DateTimeParseException e) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }
}
