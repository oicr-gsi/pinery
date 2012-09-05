package ca.on.oicr.pinery.lims;

import java.util.Date;

import ca.on.oicr.pinery.api.SampleProject;

public class DefaultSampleProject implements SampleProject {
	
	private String name;
	private Integer count;
	private Integer archivedCount;
	private Date earliest;
	private Date latest;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getCount() {
		return count;
	}

	@Override
	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public Date getEarliest() {
		return new Date(earliest.getTime());
	}

	@Override
	public void setEarliest(Date earliest) {
		this.earliest = new Date(earliest.getTime());
	}

	@Override
	public Date getLatest() {
		return new Date(latest.getTime());
	}

	@Override
	public void setLatest(Date latest) {
		this.latest = new Date(latest.getTime());
	}

	@Override
	public Integer getArchivedCount() {
		if(archivedCount == null) {
			archivedCount = 0;
		}
		return archivedCount;
	}

	@Override
	public void setArchivedCount(Integer archivedCount) {
		this.archivedCount = archivedCount;
	}

}
