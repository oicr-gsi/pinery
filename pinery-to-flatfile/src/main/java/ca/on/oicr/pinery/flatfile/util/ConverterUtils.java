package ca.on.oicr.pinery.flatfile.util;

import ca.on.oicr.ws.dto.StatusDto;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtils {

  /**
   * Maps a list of objects to their IDs for easier lookup
   *
   * @param items the objects to map
   * @param idGetter provides a means to access the object's ID, which will be used as its key
   * @return a map with the objects' IDs as keys and the objects as values
   */
  public static <T> Map<Integer, T> mapToIds(Collection<T> items, IdGetter<T> idGetter) {
    Map<Integer, T> map = new HashMap<>();
    for (T item : items) {
      map.put(idGetter.getId(item), item);
    }
    return map;
  }

  /**
   * Functional interface for extracting an object's ID
   *
   * @param <T> the type of object whose ID we need to extract
   */
  public interface IdGetter<T> {
    /**
     * Finds an item's ID
     *
     * @param item the object to extract an ID from
     * @return the item's ID
     */
    public Integer getId(T item);
  }

  public static String getStatusString(StatusDto status) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    if (status != null) {
      sb.append("name", status.getName());
      sb.append("state", status.getState());
      sb.appendNonNull("date", status.getDate());
    }
    return sb.toString();
  }

  public static String toStringOrNull(Object object) {
    return object == null ? null : object.toString();
  }
}
