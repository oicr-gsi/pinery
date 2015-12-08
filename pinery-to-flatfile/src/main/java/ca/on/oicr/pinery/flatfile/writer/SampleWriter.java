package ca.on.oicr.pinery.flatfile.writer;

import java.util.List;
import java.util.Set;

import ca.on.oicr.pinery.flatfile.util.ConverterUtils;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.SampleDto;
import ca.on.oicr.ws.dto.StatusDto;

public class SampleWriter extends Writer {
  
  private static final String[] headers = {
    "id",
    "name",
    "description",
    "tubeBarcode",
    "storageLocation",
    "sampleType",
    "createdDate",
    "createdUserId",
    "modifiedDate",
    "modifiedUserId",
    "parentSampleId",
    "projectName",
    "archived",
    "status",
    "attributes"
  };
  
  private final List<SampleDto> samples;
  
  public SampleWriter(List<SampleDto> samples) {
    this.samples = samples;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return samples.size();
  }

  @Override
  protected String[] getRecord(int row) {
    SampleDto sample = samples.get(row);
    
    String[] data = {
        sample.getId().toString(),
        sample.getName(),
        sample.getDescription(),
        sample.getTubeBarcode(),
        sample.getStorageLocation(),
        sample.getSampleType(),
        sample.getCreatedDate(),
        sample.getCreatedById() == null ? "" : sample.getCreatedById().toString(),
        sample.getModifiedDate(),
        sample.getModifiedById() == null ? "" : sample.getModifiedById().toString(),
        getParentIdString(sample),
        sample.getProjectName(),
        sample.getArchived().toString(),
        getStatusString(sample),
        getAttributesString(sample)
    };
    
    return data;
  }
  
  private static String getParentIdString(SampleDto sample) {
    String parentId = null;
    if (sample.getParents() != null) {
      for (String parent : sample.getParents()) {
        if (parentId != null) throw new RuntimeException("Sample ID " + sample.getId() + " has multiple parents");
        parentId = ConverterUtils.getIdFromUrl(parent).toString();
      }
    }
    return parentId;
  }
  
  private static String getStatusString(SampleDto sample) {
    StatusDto status = sample.getStatus();
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    if (status != null) {
      sb.append("name", status.getName());
      sb.append("state", status.getState());
    }
    return sb.toString();
  }
  
  private static String getAttributesString(SampleDto sample) {
    Set<AttributeDto> atts = sample.getAttributes();
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    
    if (atts != null) {
      for (AttributeDto att : atts) {
        sb.append(att.getName(), att.getValue());
      }
    }
    
    return sb.toString();
  }
  
}
