package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.Set;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.Status;

public class DefaultSample implements Sample {

	private String url;
	private String name;
	private String description;
	private Integer id;
	private Set<Integer> parents;
	private Set<Integer> children;
	private String sampleType;
	private String tissueType;
	private String project;
	private Set<Attribute> attributes;
	private Boolean archived;
	private Date created;
	private Integer createdById;
	private Date modified;
	private Integer modifiedById;
	private String tubeBarcode;
	private Float volume; // Why no units.
	private Float concentration;
	private String storageLocation;
	private PreparationKit preparationKit;
	private Status status;

	public Status getOrCreateStatus() {
		if(status == null) {
			status = new DefaultStatus();
		}
		return status;
	}
	
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public PreparationKit getPreparationKit() {
		return preparationKit;
	}

	public PreparationKit getOrCreatePreparationKit() {
		if (preparationKit == null) {
			preparationKit = new DefaultPreparationKit();
		}
		return preparationKit;
	}

	@Override
	public void setPreparationKit(PreparationKit preparationKit) {
		this.preparationKit = preparationKit;
	}

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
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(Set<Attribute> attributes) {
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
		return created != null ? new Date(created.getTime()) : null;
	}

	@Override
	public void setCreated(Date created) {
		this.created = created != null ? new Date(created.getTime()) : null;
	}

	@Override
	public Date getModified() {
		return modified != null ? new Date(modified.getTime()) : null;
	}

	@Override
	public void setModified(Date modified) {
		this.modified = modified != null ? new Date(modified.getTime()) : null;
	}

	@Override
	public String getTubeBarcode() {
		return tubeBarcode;
	}

	@Override
	public void setTubeBarcode(String tubeBarcode) {
		this.tubeBarcode = tubeBarcode != null && !tubeBarcode.equals("") ? tubeBarcode : null;
	}

	@Override
	public String getStorageLocation() {
		return storageLocation;
	}

	@Override
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation != null && !storageLocation.equals("") ? storageLocation : null;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getConcentration() {
		return concentration;
	}

	public void setConcentration(Float concentration) {
		this.concentration = concentration;
	}

	@Override
	public Set<Integer> getParents() {
		return parents;
	}

	@Override
	public void setParents(Set<Integer> parents) {
		this.parents = parents;
	}

	@Override
	public Set<Integer> getChildren() {
		return children;
	}

	@Override
	public void setChildren(Set<Integer> children) {
		this.children = children;
	}

	@Override
	public Integer getCreatedById() {
		return createdById;
	}

	@Override
	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}

	@Override
	public Integer getModifiedById() {
		return modifiedById;
	}

	@Override
	public void setModifiedById(Integer modifiedById) {
		this.modifiedById = modifiedById;
	}



}
