package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.lims.flatfile.dao.exception.NonUniqueKeyException;
import ca.on.oicr.pinery.lims.flatfile.dao.exception.ParseException;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Static class that provides utility methods used by DAOs */
public class DaoUtils {

  private DaoUtils() {
    throw new AssertionError("Util class is not meant to be instantiated");
  }

  /**
   * Determines the proper result of a request that expects one object to have been returned in a
   * list
   *
   * @param list the list to check
   * @return the object if there is only one object in the list; null if the list is empty
   * @throws NonUniqueKeyException if there is more than one item in the list
   */
  public static final <T> T getExpectedSingleResult(List<T> list) {
    switch (list.size()) {
      case 0:
        return null;
      case 1:
        return list.get(0);
      default:
        throw new NonUniqueKeyException(
            "Expected one matching item, but found " + list.size() + " using the same key");
    }
  }

  /**
   * Parses a String representation of a list, from a LIMS flat file
   *
   * @param string the list String to parse
   * @return a List of the Strings contained within the original list String
   */
  public static List<String> parseList(String string) {
    List<String> list = new ArrayList<>();
    if (string == null || string.length() == 0 || string.matches("^\\[\\s\\]$")) {
      // contains no data
      return list;
    }
    if (!string.matches("^\\[.*\\]$")) {
      int errorPos = string.matches("^\\[") ? string.length() - 1 : 0;
      throw ParseException.fromErrorData(string, errorPos, "Invalid list String");
    }

    int currentStringStart = 1;
    int nestLevel = 0;
    for (int i = 1; i < string.length() - 1; i++) {
      char c = string.charAt(i);
      switch (c) {
        case '[':
        case '{':
          if (!isEscaped(string, i)) {
            nestLevel += 1;
          }
          break;
        case ']':
        case '}':
          if (!isEscaped(string, i)) {
            nestLevel -= 1;
          }
          break;
        case ',':
          if (nestLevel == 0) {
            list.add(string.substring(currentStringStart, i));
            currentStringStart = i + 1;
          }
      }
    }
    if (nestLevel != 0) {
      throw ParseException.fromErrorData(string, string.length() - 1, "Improper object nesting");
    }
    String lastString = string.substring(currentStringStart, string.length() - 1);
    if (lastString.length() > 0) list.add(lastString);

    return list;
  }

  private static enum PairPart {
    KEY,
    VALUE
  }

  /**
   * Parses a String representation of a set of key:value pairs, from a LIMS flat file
   *
   * @param string the key:value pair set String to parse
   * @return a Map containing all the key:value pairs (as String:String) contained within the
   *     original pairs String
   */
  public static Map<String, String> parseKeyValuePairs(String string) {
    Map<String, String> map = new HashMap<>();
    if (string == null || string.length() == 0 || string.matches("^\\{\\s*\\}$")) {
      // contains no data
      return map;
    }
    if (!string.matches("^\\{.*\\}$")) {
      int errorPos = string.matches("^\\{") ? string.length() - 1 : 0;
      throw ParseException.fromErrorData(string, errorPos, "String not enclosed in {}");
    }

    int currentStringStart = 1;
    int nestLevel = 0;
    PairPart readingPart = PairPart.KEY;
    String key = null;
    String value = null;

    for (int i = 1; i < string.length() - 1; i++) {
      char c = string.charAt(i);
      switch (readingPart) {
        case KEY:
          switch (c) {
            case '[':
            case ']':
            case '{':
            case '}':
              throw ParseException.fromErrorData(string, i, "Key contains nested items");
            case '=':
              key = string.substring(currentStringStart, i);
              currentStringStart = i + 1;
              readingPart = PairPart.VALUE;
              break;
          }
          break;
        case VALUE:
          switch (c) {
            case '{':
            case '[':
              if (!isEscaped(string, i)) {
                nestLevel += 1;
              }
              break;
            case '}':
            case ']':
              if (!isEscaped(string, i)) {
                nestLevel -= 1;
              }
              break;
            case '|':
              if (!isEscaped(string, i) && nestLevel == 0) {
                value = unescapeValue(string.substring(currentStringStart, i));
                currentStringStart = i + 1;
                map.put(key, value);
                key = null;
                value = null;
                readingPart = PairPart.KEY;
              }
              break;
          }
          break;
        default:
          throw new AssertionError("Bad logic in parse method. Unspecified read part");
      }
    }
    if (readingPart != PairPart.VALUE) {
      throw ParseException.fromErrorData(
          string, string.length() - 1, "Does not end in key=value pair");
    }
    value = unescapeValue(string.substring(currentStringStart, string.length() - 1));
    map.put(key, value);

    return map;
  }

  private static boolean isEscaped(String string, int position) {
    if (position == 1 || string.charAt(position - 1) != '\\') {
      return false;
    }
    if (position < 3) {
      return true;
    }
    // if number of (\) is odd, it is escaped
    boolean escaped = true;
    for (int i = position - 2; i > 0; i--) {
      if (string.charAt(i) == '\\') {
        escaped = !escaped;
      } else {
        break;
      }
    }
    return escaped;
  }

  private static String unescapeValue(String escaped) {
    return escaped
        .replace("\\{", "{")
        .replace("\\}", "}")
        .replace("\\[", "[")
        .replace("\\]", "]")
        .replace("\\|", "|")
        .replace("\\\\", "\\");
  }

  public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
    ResultSetMetaData metadata = rs.getMetaData();
    for (int i = 1; i <= metadata.getColumnCount(); i++) {
      if (columnName.equals(metadata.getColumnName(i))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets a String from the ResultSet if the column exists and has a value. Useful to maintain
   * backwards compatibility with flatfiles created with a previous version of Pinery-to-flatfile
   * which may not have included the column in question
   *
   * @param rs the ResultSet to check
   * @param columnName the name of the column to check for
   * @return the column value if the column is present and is not null or empty; otherwise null
   * @throws SQLException
   */
  public static String getStringIfPresent(ResultSet rs, String columnName) throws SQLException {
    if (!hasColumn(rs, columnName)) {
      return null;
    }
    return ModelUtils.nullIfEmpty(rs.getString(columnName));
  }
}
