package eu.mrndesign.matned.portfolioapp.statics;

import java.time.format.DateTimeFormatter;

public class Patterns {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_TASK = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter DATE_TIME_FORMATTER_ONLY_DATE = DateTimeFormatter.ofPattern(DATE_PATTERN);

}
