package pt.dcs.unbabel.bec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public interface DateTimeUtil {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    static LocalDateTime roundCeilingToTheMinute(LocalDateTime in) {
        return in.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
    }

    static LocalDateTime roundFloorToTheMinute(LocalDateTime in) {
        return in.truncatedTo(ChronoUnit.MINUTES);
    }

    static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    static String format(LocalDateTime date) {
        return date.format(dateTimeFormatter);
    }

}
