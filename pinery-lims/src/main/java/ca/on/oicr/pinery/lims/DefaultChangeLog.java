package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import java.util.Set;

public class DefaultChangeLog implements ChangeLog {

  private String sampleId;
  private Set<Change> changes;

  @Override
  public String getSampleId() {
    return sampleId;
  }

  @Override
  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  @Override
  public Set<Change> getChanges() {
    return changes;
  }

  @Override
  public void setChanges(Set<Change> changes) {
    this.changes = changes;
  }
}
