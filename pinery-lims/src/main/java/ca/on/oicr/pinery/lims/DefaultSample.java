package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.List;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.PreparationKit;
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
	private String tubeBarcode;
	private String volume; // Why no units.
	private String concentration;
	private String storageLocation;
	private PreparationKit preparationKit;

	@Override
	public PreparationKit getPreparationKit() {
		if(preparationKit == null) {
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
	public String getVolume() {
		return volume;
	}

	@Override
	public void setVolume(String volume) {
		this.volume = volume != null && !volume.equals("") ? volume : null;
	}

	@Override
	public String getConcentration() {
		return concentration;
	}

	@Override
	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}

	@Override
	public String getStorageLocation() {
		return storageLocation;
	}

	@Override
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation != null && !storageLocation.equals("") ? storageLocation : null;
	}
}
