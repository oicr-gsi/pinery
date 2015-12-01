package ca.on.oicr.pinery.lims.flatfile.model;

import java.util.Date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class ModelUtils {
  
  //"2012-06-12T14:47:09-04:00"
  public static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendYear(4, 4)
      .appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).appendLiteral('T')
      .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2).appendLiteral(':').appendSecondOfMinute(2)
      .appendTimeZoneOffset(null, true, 1, 1).toFormatter();
  
  private ModelUtils() {
    throw new AssertionError("Util class is not meant to be instantiated");
  }
  
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
