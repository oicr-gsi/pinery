package ca.on.oicr.pinery.lims.flatfile.model;

import java.util.Date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class ModelUtils {
  
  //"2012-06-12 14:47:09-04"
  public static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendYear(4, 4)
      .appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).appendLiteral(' ')
      .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2).appendLiteral(':').appendSecondOfMinute(2)
      .appendTimeZoneOffset(null, true, 1, 1).toFormatter();
  
  /**
   * @param date
   * @return
   * @throws IllegalArgumentException
   */
  public static Date convertToDate(String date) {
    if (date == null || "".equals(date)) {
      return null;
    }
    else {
      return dateTimeFormatter.parseDateTime(date).toDate();
    }
  }
  
}
