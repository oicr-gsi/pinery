package ca.on.oicr.pinery.api;

import java.util.Date;

public interface SampleProject {

	public String getName();
	public void setName(String name);
	public Integer getCount();
	public void setCount(Integer count);
	public Integer getArchivedCount();
	public void setArchivedCount(Integer count);
	public Date getEarliest();
	public void setEarliest(Date earliest);
	public Date getLatest();
	public void setLatest(Date latest);

}
