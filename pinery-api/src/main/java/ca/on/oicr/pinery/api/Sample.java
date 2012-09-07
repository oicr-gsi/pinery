package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.List;

public interface Sample {

	public String getUrl();
	public void setUrl(String url);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public Integer getId();
	public void setId(Integer id);
	public List<Integer> getParents();
	public void setParents(List<Integer> parents);
	public List<Integer> getChildren();
	public void setChildren(List<Integer> children);
	public String getSampleType();
	public void setSampleType(String sampleType);
	public String getTissueType();
	public void setTissueType(String tissueType);
	public String getProject();
	public void setProject(String project);
	public List<Attribute> getAttributes();
	public void setAttributes(List<Attribute> attributes);
	public Boolean getArchived();
	public void setArchived(Boolean archived);
	public Date getCreated();
	public void setCreated(Date created);
	public Date getModified() ;
	public void setModified(Date modified);
	public String getTubeBarcode();
	public void setTubeBarcode(String tubeBarcode);
	public String getVolume();
	public void setVolume(String volume);
	public String getConcentration();
	public void setConcentration(String concentration);
	public String getStorageLocation();
	public void setStorageLocation(String storageLocation);
	public PreparationKit getPreparationKit();
	public void setPreparationKit(PreparationKit preparationKit);
	
}
