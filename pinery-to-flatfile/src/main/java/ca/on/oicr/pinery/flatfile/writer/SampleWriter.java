package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.PreparationKitDto;
import ca.on.oicr.ws.dto.SampleDto;
import ca.on.oicr.ws.dto.SampleReferenceDto;
import ca.on.oicr.ws.dto.StatusDto;
import java.util.List;
import java.util.Set;

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
    "parentIds",
    "childIds",
    "projectName",
    "archived",
    "status",
    "volume",
    "concentration",
    "preparationKit",
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
      getIdsString(sample.getParents()),
      getIdsString(sample.getChildren()),
      sample.getProjectName(),
      sample.getArchived().toString(),
      getStatusString(sample),
      sample.getVolume() == null ? "" : sample.getVolume().toString(),
      sample.getConcentration() == null ? "" : sample.getConcentration().toString(),
      getPreparationKitString(sample),
      getAttributesString(sample)
    };

    return data;
  }

  private String getPreparationKitString(SampleDto sample) {
    PreparationKitDto kit = sample.getPreparationKit();
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    if (kit != null) {
      if (kit.getName() != null) sb.append("name", kit.getName());
      if (kit.getDescription() != null) sb.append("description", kit.getDescription());
    }
    return sb.toString();
  }

  private static String getIdsString(Set<SampleReferenceDto> samples) {
    ArrayStringBuilder sb = new ArrayStringBuilder();
    if (samples != null) {
      for (SampleReferenceDto sample : samples) {
        sb.append(sample.getId());
      }
    }
    return sb.toString();
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
