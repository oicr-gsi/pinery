package ca.on.oicr.pinery.lims;

import java.util.Set;

import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;

public class DefaultChangeLog implements ChangeLog {

	private Integer sampleId;
	private Set<Change> changes;

	public Integer getSampleId() {
		return sampleId;
	}

	public void setSampleId(Integer sampleId) {
		this.sampleId = sampleId;
	}

	public Set<Change> getChanges() {
		return changes;
	}

	public void setChanges(Set<Change> changes) {
		this.changes = changes;
	}

}
