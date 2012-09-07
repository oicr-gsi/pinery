package ca.on.oicr.ws.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "Sample")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class SampleDto {

	private String url;
	private String name;
	private String description;
	@JsonProperty("tube_barcode")
	private String tubeBarcode;
	private Float volume;
	private Float concentration;
	@JsonProperty("storage_location")
	private String storageLocation;
	@JsonProperty("created_date")
	private String createdDate;
	@JsonProperty("modified_date")
	private String modifiedDate;
	private Integer id;
	private Boolean archived;
	@JsonProperty("preparation_kit")
	private PreparationKitDto preparationKit;

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

	public String getTubeBarcode() {
		return tubeBarcode;
	}

	public void setTubeBarcode(String tubeBarcode) {
		this.tubeBarcode = tubeBarcode;
	}



	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

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

}
