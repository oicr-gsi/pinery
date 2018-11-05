package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RunDto {

   private String state;
   private String name;
   private String barcode;
   private String instrumentName;
   private Set<RunDtoPosition> positions;
   private String readLength;
   private Integer createdById;
   private String createdByUrl;
   private String createdDate;
   private Integer id;
   private String url;
   private Integer instrumentId;
   private String instrumentUrl;
   private String startDate;
   private String completionDate;
   private Integer modifiedById;
   private String modifiedByUrl;
   private String modifiedDate;
   private String runDirectory;
   private String runBasesMask;
   private String sequencingParameters;

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public Set<RunDtoPosition> getPositions() {
      return positions;
   }

   public void setPositions(Set<RunDtoPosition> positions) {
      this.positions = positions;
   }

   @JsonProperty("read_length")
   public String getReadLength() {
     return readLength;
   }

   public void setReadLength(String readLength) {
     this.readLength = readLength;
   }

   @JsonProperty("created_by_id")
   public Integer getCreatedById() {
      return createdById;
   }

   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }

   @JsonProperty("created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @JsonProperty("created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @JsonProperty("instrument_name")
   public String getInstrumentName() {
      return instrumentName;
   }

   public void setInstrumentName(String instrumentName) {
      this.instrumentName = instrumentName;
   }

   @JsonProperty("instrument_id")
   public Integer getInstrumentId() {
      return instrumentId;
   }

   public void setInstrumentId(Integer instrumentId) {
      this.instrumentId = instrumentId;
   }

   @JsonProperty("instrument_url")
   public String getInstrumentUrl() {
      return instrumentUrl;
   }

   public void setInstrumentUrl(String instrumentUrl) {
      this.instrumentUrl = instrumentUrl;
   }

  @JsonProperty("start_date")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @JsonProperty("completion_date")
  public String getCompletionDate() {
    return completionDate;
  }

  public void setCompletionDate(String completionDate) {
    this.completionDate = completionDate;
  }

  @JsonProperty("modified_by_id")
  public Integer getModifiedById() {
    return modifiedById;
  }

  public void setModifiedById(Integer modifiedById) {
    this.modifiedById = modifiedById;
  }

  @JsonProperty("modified_by_url")
  public String getModifiedByUrl() {
    return modifiedByUrl;
  }

  public void setModifiedByUrl(String modifiedByUrl) {
    this.modifiedByUrl = modifiedByUrl;
  }

  @JsonProperty("modified_date")
  public String getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  @JsonProperty("run_directory")
  public String getRunDirectory() {
    return runDirectory;
  }

  public void setRunDirectory(String runDirectory) {
    this.runDirectory = runDirectory;
  }

  @JsonProperty("run_bases_mask")
  public String getRunBasesMask() {
    return runBasesMask;
  }

  public void setRunBasesMask(String runBasesMask) {
    this.runBasesMask = runBasesMask;
  }
  
  @JsonProperty("sequencing_parameters")
  public String getSequencingParameters() {
    return sequencingParameters;
  }
  
  public void setSequencingParameters(String sequencingParameters) {
    this.sequencingParameters = sequencingParameters;
  }

@Override
  public String toString() {
    return "RunDto [state=" + state + ", name=" + name + ", barcode=" + barcode
        + ", instrumentName=" + instrumentName + ", positions=" + positions
        + ", createdById=" + createdById + ", createdByUrl=" + createdByUrl
        + ", createdDate=" + createdDate + ", id=" + id + ", url=" + url
        + ", instrumentId=" + instrumentId + ", instrumentUrl=" + instrumentUrl
        + ", startDate=" + startDate + ", completionDate=" + completionDate
        + ", modifiedById=" + modifiedById + ", modifiedByUrl=" + modifiedByUrl
        + ", modifiedDate=" + modifiedDate + ", runDirectory=" + runDirectory
        + ", runBasesMask=" + runBasesMask + ", sequencingParameters=" 
        + sequencingParameters + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result
        + ((completionDate == null) ? 0 : completionDate.hashCode());
    result = prime * result
        + ((createdById == null) ? 0 : createdById.hashCode());
    result = prime * result
        + ((createdDate == null) ? 0 : createdDate.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result
        + ((instrumentId == null) ? 0 : instrumentId.hashCode());
    result = prime * result
        + ((instrumentName == null) ? 0 : instrumentName.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((positions == null) ? 0 : positions.hashCode());
    result = prime * result
        + ((readLength == null) ? 0 : readLength.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((runDirectory == null) ? 0 : runDirectory.hashCode());
    result = prime * result + ((runBasesMask == null) ? 0 : runBasesMask.hashCode());
    result = prime * result + ((sequencingParameters == null) ? 0 : sequencingParameters.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RunDto other = (RunDto) obj;
    if (barcode == null) {
      if (other.barcode != null)
        return false;
    }
    else if (!barcode.equals(other.barcode))
      return false;
    if (completionDate == null) {
      if (other.completionDate != null)
        return false;
    }
    else if (!completionDate.equals(other.completionDate))
      return false;
    if (createdById == null) {
      if (other.createdById != null)
        return false;
    }
    else if (!createdById.equals(other.createdById))
      return false;
    if (createdDate == null) {
      if (other.createdDate != null)
        return false;
    }
    else if (!createdDate.equals(other.createdDate))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    }
    else if (!id.equals(other.id))
      return false;
    if (instrumentId == null) {
      if (other.instrumentId != null)
        return false;
    }
    else if (!instrumentId.equals(other.instrumentId))
      return false;
    if (instrumentName == null) {
      if (other.instrumentName != null)
        return false;
    }
    else if (!instrumentName.equals(other.instrumentName))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    if (positions == null) {
      if (other.positions != null)
        return false;
    }
    else if (!positions.equals(other.positions))
      return false;
    if (readLength == null) {
      if (other.readLength != null)
        return false;
    }
    else if (!readLength.equals(other.readLength))
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    }
    else if (!startDate.equals(other.startDate))
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    }
    else if (!state.equals(other.state))
      return false;
    if (runDirectory == null) {
      if (other.runDirectory != null)
        return false;
    }
    else if (!runDirectory.equals(other.runDirectory))
      return false;
    if (runBasesMask == null) {
      if (other.runBasesMask != null)
        return false;
    }
    else if (!runBasesMask.equals(other.runBasesMask))
      return false;
    if (sequencingParameters == null) {
        if (other.sequencingParameters != null)
          return false;
      }
      else if (!sequencingParameters.equals(other.sequencingParameters))
        return false;
    return true;
  }

}
