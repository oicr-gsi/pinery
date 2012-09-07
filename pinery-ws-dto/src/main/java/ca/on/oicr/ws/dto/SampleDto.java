package ca.on.oicr.ws.dto;

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
	private String modifiedDate;
	private Integer id;
	private Boolean archived;
	private PreparationKitDto preparationKit;
	private String projectName;
	private String sampleType;

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

	@XmlElement(name="creation_date")
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



}
