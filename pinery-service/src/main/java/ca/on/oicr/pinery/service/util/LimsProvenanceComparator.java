package ca.on.oicr.pinery.service.util;

import ca.on.oicr.gsi.provenance.model.LimsProvenance;
import com.google.common.primitives.Ints;
import java.util.Comparator;

/** @author mlaszloffy */
public class LimsProvenanceComparator implements Comparator<LimsProvenance> {

  @Override
  public int compare(LimsProvenance p1, LimsProvenance p2) {
    String[] p1_ids = p1.getProvenanceId().split("_");
    String[] p2_ids = p2.getProvenanceId().split("_");

    int cmp = 0;
    for (int i = 0; i < p1_ids.length && i < p2_ids.length; i++) {
      Integer left = Ints.tryParse(p1_ids[i]);
      Integer right = Ints.tryParse(p2_ids[i]);
      if (left != null && right != null) {
        cmp = left - right;
      } else {
        cmp = p1_ids[i].compareTo(p2_ids[i]);
      }
      if (cmp != 0) {
        return cmp;
      }
    }
    return p1.getProvenanceId().length() - p2.getProvenanceId().length();
  }
}
