package ca.on.oicr.pinery.api;

import java.util.Set;

public interface ChangeLog {

	public String getSampleId();

	public void setSampleId(String sampleId);

	public Set<Change> getChanges();

	public void setChanges(Set<Change> changes);
}
