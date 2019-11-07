package ca.on.oicr.pinery.ws.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<A, B> {

  private final Map<A, B> map = new HashMap<>();

  public MapBuilder<A, B> put(A key, B value) {
    map.put(key, value);
    return this;
  }

  public Map<A, B> build() {
    return map;
  }
}
