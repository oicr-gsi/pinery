package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.Set;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.Status;

public class DefaultSample implements Sample {

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((archived == null) ? 0 : archived.hashCode());
      result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
      result = prime * result + ((children == null) ? 0 : children.hashCode());
      result = prime * result + ((concentration == null) ? 0 : concentration.hashCode());
      result = prime * result + ((created == null) ? 0 : created.hashCode());
      result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((modified == null) ? 0 : modified.hashCode());
      result = prime * result + ((modifiedById == null) ? 0 : modifiedById.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((parents == null) ? 0 : parents.hashCode());
      result = prime * result + ((preparationKit == null) ? 0 : preparationKit.hashCode());
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      result = prime * result + ((sampleType == null) ? 0 : sampleType.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((storageLocation == null) ? 0 : storageLocation.hashCode());
      result = prime * result + ((tissueType == null) ? 0 : tissueType.hashCode());
      result = prime * result + ((tubeBarcode == null) ? 0 : tubeBarcode.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      result = prime * result + ((volume == null) ? 0 : volume.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultSample other = (DefaultSample) obj;
      if (archived == null) {
         if (other.archived != null) return false;
      } else if (!archived.equals(other.archived)) return false;
      if (attributes == null) {
         if (other.attributes != null) return false;
      } else if (!attributes.equals(other.attributes)) return false;
      if (children == null) {
         if (other.children != null) return false;
      } else if (!children.equals(other.children)) return false;
      if (concentration == null) {
         if (other.concentration != null) return false;
      } else if (!concentration.equals(other.concentration)) return false;
      if (created == null) {
         if (other.created != null) return false;
      } else if (!created.equals(other.created)) return false;
      if (createdById == null) {
         if (other.createdById != null) return false;
      } else if (!createdById.equals(other.createdById)) return false;
      if (description == null) {
         if (other.description != null) return false;
      } else if (!description.equals(other.description)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (modified == null) {
         if (other.modified != null) return false;
      } else if (!modified.equals(other.modified)) return false;
      if (modifiedById == null) {
         if (other.modifiedById != null) return false;
      } else if (!modifiedById.equals(other.modifiedById)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (parents == null) {
         if (other.parents != null) return false;
      } else if (!parents.equals(other.parents)) return false;
      if (preparationKit == null) {
         if (other.preparationKit != null) return false;
      } else if (!preparationKit.equals(other.preparationKit)) return false;
      if (project == null) {
         if (other.project != null) return false;
      } else if (!project.equals(other.project)) return false;
      if (sampleType == null) {
         if (other.sampleType != null) return false;
      } else if (!sampleType.equals(other.sampleType)) return false;
      if (status == null) {
         if (other.status != null) return false;
      } else if (!status.equals(other.status)) return false;
      if (storageLocation == null) {
         if (other.storageLocation != null) return false;
      } else if (!storageLocation.equals(other.storageLocation)) return false;
      if (tissueType == null) {
         if (other.tissueType != null) return false;
      } else if (!tissueType.equals(other.tissueType)) return false;
      if (tubeBarcode == null) {
         if (other.tubeBarcode != null) return false;
      } else if (!tubeBarcode.equals(other.tubeBarcode)) return false;
      if (url == null) {
         if (other.url != null) return false;
      } else if (!url.equals(other.url)) return false;
      if (volume == null) {
         if (other.volume != null) return false;
      } else if (!volume.equals(other.volume)) return false;
      return true;
   }

   protected String url;
   protected String name;
   protected String description;
   protected String id;
   protected Set<Integer> parents;
   protected Set<Integer> children;
   protected String sampleType;
   protected String tissueType;
   protected String project;
   protected Set<Attribute> attributes;
   protected Boolean archived;
   protected Date created;
   protected Integer createdById;
   protected Date modified;
   protected Integer modifiedById;
   protected String tubeBarcode;
   protected Float volume; // Why no units.
   protected Float concentration;
   protected String storageLocation;
   protected PreparationKit preparationKit;
   protected Status status;

   public Status getOrCreateStatus() {
      if (status == null) {
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
   public String getId() {
      return id;
   }

   @Override
   public void setId(String id) {
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

   @Override
   public Float getVolume() {
      return volume;
   }

   @Override
   public void setVolume(Float volume) {
      this.volume = volume;
   }

   @Override
   public Float getConcentration() {
      return concentration;
   }

   @Override
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
