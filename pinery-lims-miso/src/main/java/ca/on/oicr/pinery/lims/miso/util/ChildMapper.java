package ca.on.oicr.pinery.lims.miso.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.on.oicr.pinery.lims.miso.ChildObject;
import ca.on.oicr.pinery.lims.miso.ParentObject;

public class ChildMapper<T extends ParentObject<U>, U extends ChildObject> {

  List<T> parents;
  
  public ChildMapper(List<T> parents) {
    this.parents = parents;
  }
  
  public List<T> mapChildren(List<U> children) {
    Map<Integer, T> map = new HashMap<>();
    for (T parent : parents) {
      map.put(parent.getId(), parent);
    }
    for (U child : children) {
      T parent = map.get(child.getParentId());
      if (parent != null) {
        parent.addChild(child);
      }
    }
    return null;
  }
  
}
