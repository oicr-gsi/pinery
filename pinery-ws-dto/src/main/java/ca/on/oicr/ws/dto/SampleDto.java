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

   @Override
   public String toString() {
      return "SampleDto [name=" + name + ", description=" + description + ", tubeBarcode=" + tubeBarcode + ", volume=" + volume
            + ", concentration=" + concentration + ", storageLocation=" + storageLocation + ", createdDate=" + createdDate
            + ", modifiedDate=" + modifiedDate + ", id=" + id + ", archived=" + archived + ", projectName=" + projectName + ", sampleType="
            + sampleType + ", status=" + status + "]";
   }

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

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((archived == null) ? 0 : archived.hashCode());
      result = prime * result + ((concentration == null) ? 0 : concentration.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
      result = prime * result + ((sampleType == null) ? 0 : sampleType.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((storageLocation == null) ? 0 : storageLocation.hashCode());
      result = prime * result + ((tubeBarcode == null) ? 0 : tubeBarcode.hashCode());
      result = prime * result + ((volume == null) ? 0 : volume.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      SampleDto other = (SampleDto) obj;
      if (archived == null) {
         if (other.archived != null) return false;
      } else if (!archived.equals(other.archived)) return false;
      if (concentration == null) {
         if (other.concentration != null) return false;
      } else if (!concentration.equals(other.concentration)) return false;
      if (createdDate == null) {
         if (other.createdDate != null) return false;
      } else if (!createdDate.equals(other.createdDate)) return false;
      if (description == null) {
         if (other.description != null) return false;
      } else if (!description.equals(other.description)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (modifiedDate == null) {
         if (other.modifiedDate != null) return false;
      } else if (!modifiedDate.equals(other.modifiedDate)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (projectName == null) {
         if (other.projectName != null) return false;
      } else if (!projectName.equals(other.projectName)) return false;
      if (sampleType == null) {
         if (other.sampleType != null) return false;
      } else if (!sampleType.equals(other.sampleType)) return false;
      if (status == null) {
         if (other.status != null) return false;
      } else if (!status.equals(other.status)) return false;
      if (storageLocation == null) {
         if (other.storageLocation != null) return false;
      } else if (!storageLocation.equals(other.storageLocation)) return false;
      if (tubeBarcode == null) {
         if (other.tubeBarcode != null) return false;
      } else if (!tubeBarcode.equals(other.tubeBarcode)) return false;
      if (volume == null) {
         if (other.volume != null) return false;
      } else if (!volume.equals(other.volume)) return false;
      return true;
   }

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

   @XmlElement(name = "tube_barcode")
   public String getTubeBarcode() {
      return tubeBarcode;
   }

   public void setTubeBarcode(String tubeBarcode) {
      this.tubeBarcode = tubeBarcode;
   }

   @XmlElement(name = "storage_location")
   public String getStorageLocation() {
      return storageLocation;
   }

   public void setStorageLocation(String storageLocation) {
      this.storageLocation = storageLocation;
   }

   @XmlElement(name = "created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @XmlElement(name = "modified_date")
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

   @XmlElement(name = "preparation_kit")
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

   @XmlElement(name = "project_name")
   public String getProjectName() {
      return projectName;
   }

   public void setProjectName(String projectName) {
      this.projectName = projectName;
   }

   @XmlElement(name = "sample_type")
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

   @XmlElement(name = "created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @XmlElement(name = "modified_by_url")
   public String getModifiedByUrl() {
      return modifiedByUrl;
   }

   public void setModifiedByUrl(String modifiedByUrl) {
      this.modifiedByUrl = modifiedByUrl;
   }

}
