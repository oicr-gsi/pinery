package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.on.oicr.pinery.lims.flatfile.dao.exception.NonUniqueKeyException;
import ca.on.oicr.pinery.lims.flatfile.dao.exception.ParseException;

public class DaoUtils {
  
  private DaoUtils() {
    throw new AssertionError("Util class is not meant to be instantiated");
  }

  public static final <T> T getExpectedSingleResult(List<T> list) {
    switch (list.size()) {
    case 0:
      return null;
    case 1:
      return list.get(0);
    default:
      throw new NonUniqueKeyException("Expected one matching item, but found " + list.size() + " using the same key");
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
  
  private static enum ReadSection {KEY, VALUE}
  
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
