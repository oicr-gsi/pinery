package ca.on.oicr.pinery.lims.flatfile.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class ModelUtils {
  
  //"2012-06-12T14:47:09-04:00"
  public static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendYear(4, 4)
      .appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).appendLiteral('T')
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
  
  public static List<String> parseList(String string) {
    List<String> list = new ArrayList<>();
    if (string.matches("^\\[\\s\\]$")) {
      // contains no data
      return list;
    }
    if (!string.matches("^\\[.*\\]$")) {
      int errorPos = string.matches("^\\[") ? string.length()-1 : 0;
      throw ParseException.fromErrorData(string, errorPos, "Invalid list String");
    }
    
    int currentStringStart = 1;
    int nestLevel = 0;
    for (int i = 1; i < string.length()-1; i++) {
      char c = string.charAt(i);
      switch (c) {
      case '[':
      case '{':
        nestLevel += 1;
        break;
      case ']':
      case '}':
        nestLevel -= 1;
        break;
      case ',':
        if (nestLevel == 0) {
          list.add(string.substring(currentStringStart, i));
          currentStringStart = i+1;
        }
      }
    }
    if (nestLevel != 0) {
      throw ParseException.fromErrorData(string, string.length()-1, "Improper object nesting");
    }
    String lastString = string.substring(currentStringStart, string.length()-1);
    if (lastString.length() > 0) list.add(lastString);
    
    return list;
  }
  
  private static enum ReadSection {KEY, VALUE};
  
  public static Map<String, String> parseKeyValuePairs(String string) {
    Map<String, String> map = new HashMap<>();
    if (string.matches("^\\{\\s*\\}$")) {
      // contains no data
      return map;
    }
    if (!string.matches("^\\{.*\\}$")) {
      int errorPos = string.matches("^\\{") ? string.length()-1 : 0;
      throw ParseException.fromErrorData(string, errorPos, "String not enclosed in {}");
    }
    
    int currentStringStart = 1;
    int nestLevel = 0;
    ReadSection readSection = ReadSection.KEY;
    String key = null;
    String value = null;
    
    for (int i = 1; i < string.length()-1; i++) {
      char c = string.charAt(i);
      switch (readSection) {
      case KEY:
        switch (c) {
        case '[': case ']': case '{': case '}':
          throw ParseException.fromErrorData(string, i, "Key contains nested items");
        case '=':
          key = string.substring(currentStringStart, i);
          currentStringStart = i+1;
          readSection = ReadSection.VALUE;
          break;
        }
        break;
      case VALUE:
        switch (c) {
        case '{': case '[':
          nestLevel += 1;
          break;
        case '}': case ']':
          nestLevel -= 1;
          break;
        case '|':
          if (nestLevel == 0) {
            value = string.substring(currentStringStart, i);
            currentStringStart = i+1;
            map.put(key, value);
            key = null;
            value = null;
            readSection = ReadSection.KEY;
          }
          break;
        }
        break;
      }
    }
    if (readSection != ReadSection.VALUE) {
      throw ParseException.fromErrorData(string, string.length()-1, "Does not end in key=value pair");
    }
    value = string.substring(currentStringStart, string.length()-1);
    map.put(key, value);
    
    return map;
  }
  
}
