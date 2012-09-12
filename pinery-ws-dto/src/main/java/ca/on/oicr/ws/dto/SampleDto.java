package ca.on.oicr.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "sample")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class SampleDto {

	private String url;
	private String name;
	private String description;
	private String tubeBarcode;
	private Float volume;
	private Float concentration;
	private String storageLocation;
	private String createdDate;
	private String createdByUrl;
	private String modifiedDate;
	private String modifiedByUrl;
	private Integer id;
	private Boolean archived;
	private PreparationKitDto preparationKit;
	private String projectName;
	private String sampleType;
	private Set<AttributeDto> attributes;
	private StatusDto status;
	private Set<String> children;
	private Set<String> parents;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement(name="tube_barcode")
	public String getTubeBarcode() {
		return tubeBarcode;
	}

	public void setTubeBarcode(String tubeBarcode) {
		this.tubeBarcode = tubeBarcode;
	}



	@XmlElement(name="storage_location")
	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	@XmlElement(name="created_date")
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@XmlElement(name="modified_date")
	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	@XmlElement(name="preparation_kit")
	public PreparationKitDto getPreparationKit() {
		return preparationKit;
	}

	public void setPreparationKit(PreparationKitDto preparationKit) {
		this.preparationKit = preparationKit;
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

	@XmlElement(name="project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@XmlElement(name="sample_type")
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public Set<AttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<AttributeDto> attributes) {
		this.attributes = attributes;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

	public Set<String> getChildren() {
		return children;
	}

	public void setChildren(Set<String> children) {
		this.children = children;
	}

	public Set<String> getParents() {
		return parents;
	}

	public void setParents(Set<String> parents) {
		this.parents = parents;
	}

	@XmlElement(name="created_by_url")
	public String getCreatedByUrl() {
		return createdByUrl;
	}

	public void setCreatedByUrl(String createdByUrl) {
		this.createdByUrl = createdByUrl;
	}

	@XmlElement(name="modified_by_url")
	public String getModifiedByUrl() {
		return modifiedByUrl;
	}

	public void setModifiedByUrl(String modifiedByUrl) {
		this.modifiedByUrl = modifiedByUrl;
	}

}
