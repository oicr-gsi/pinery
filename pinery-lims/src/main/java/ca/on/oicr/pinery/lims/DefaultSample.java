package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.List;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Sample;

public class DefaultSample implements Sample {
	
	private String url;
	private String name;
	private String description;
	private Integer id;
	private List<Integer> parents;
	private List<Integer> children;
	private String sampleType;
	private String tissueType;
	private String project;
	private List<Attribute> attributes;
	private Boolean archived;
	private Date created;
	private Date modified;
	
	@Override
	public String getUrl() {
		return url;
	}
	@Override
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public List<Integer> getParents() {
		return parents;
	}
	@Override
	public void setParents(List<Integer> parents) {
		this.parents = parents;
	}
	@Override
	public List<Integer> getChildren() {
		return children;
	}
	@Override
	public void setChildren(List<Integer> children) {
		this.children = children;
	}
	@Override
	public String getSampleType() {
		return sampleType;
	}
	@Override
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	@Override
	public String getTissueType() {
		return tissueType;
	}
	@Override
	public void setTissueType(String tissueType) {
		this.tissueType = tissueType;
	}
	@Override
	public String getProject() {
		return project;
	}
	@Override
	public void setProject(String project) {
		this.project = project;
	}
	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}
	@Override
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	@Override
	public Boolean getArchived() {
		return archived;
	}
	@Override
	public void setArchived(Boolean archived) {
		this.archived = archived;
	}
	@Override
	public Date getCreated() {
		return new Date(created.getTime());
	}
	@Override
	public void setCreated(Date created) {
		this.created = created;
	}
	@Override
	public Date getModified() {
		return new Date(modified.getTime());
	}
	@Override
	public void setModified(Date modified) {
		this.modified = modified;
	}



}
