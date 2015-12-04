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
   * Creates a Date object from String representation
   * 
   * @param date String date representation in format yyyy-mm-ddThh:mm:ss+ZZ:zz (e.g. "2012-06-12T14:47:09-04:00")
   * @return null if date is null or empty; otherwise, the Date represented by the provided String
   * @throws IllegalArgumentException if the String does not match the expected format
   */
  public static Date convertToDate(String date) {
    if (date == null || "".equals(date)) {
      return null;
    }
    else {
      return dateTimeFormatter.parseDateTime(date).toDate();
    }
  }
  
  /**
   * Utility method to eliminate empty Strings
   * 
   * @param s String to check
   * @return null if s is null or an empty String; otherwise s
   */
  public static String nullIfEmpty(String s) {
    return s == null || s.length() == 0 ? null : s;
  }
  
  /**
   * Utility method to eliminate zero-valued Integers
   * 
   * @param i the Integer to check
   * @return null if i is null or has a value of 0; otherwise i
   */
  public static Integer nullIfZero(Integer i) {
    return i == null || i.equals(Integer.valueOf(0)) ? null : i;
  }
  
}
