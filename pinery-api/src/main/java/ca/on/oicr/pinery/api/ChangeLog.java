package ca.on.oicr.pinery.api;

import java.util.Set;

public interface ChangeLog {

	public Integer getSampleId();

	public void setSampleId(Integer sampleId);

	public Set<Change> getChanges();

	public void setChanges(Set<Change> changes);
}
