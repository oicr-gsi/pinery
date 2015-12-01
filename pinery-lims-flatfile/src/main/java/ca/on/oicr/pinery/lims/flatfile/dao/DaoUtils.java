package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.lims.flatfile.dao.exception.NonUniqueKeyException;

public class DaoUtils {
  
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
  
}
