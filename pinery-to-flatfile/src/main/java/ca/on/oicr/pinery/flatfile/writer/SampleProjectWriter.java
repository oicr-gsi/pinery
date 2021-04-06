package ca.on.oicr.pinery.flatfile.writer;

import static ca.on.oicr.pinery.flatfile.util.ConverterUtils.toStringOrNull;

import ca.on.oicr.ws.dto.SampleProjectDto;
import java.util.List;

public class SampleProjectWriter extends Writer {

  private static final String[] headers = {
    "projectName",
    "active",
    "pipeline",
    "secondaryNamingScheme",
    "created",
    "rebNumber",
    "rebExpiry",
    "description",
    "samplesExpected",
    "contactName",
    "contactEmail"
  };

  private final List<SampleProjectDto> projects;

  public SampleProjectWriter(List<SampleProjectDto> boxes) {
    this.projects = boxes;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return projects.size();
  }

  @Override
  protected String[] getRecord(int row) {
    SampleProjectDto project = projects.get(row);

    String[] data = {
      project.getName(),
      Boolean.toString(project.isActive()),
      project.getPipeline(),
      Boolean.toString(project.isSecondaryNamingSCheme()),
      project.getCreatedDate(),
      project.getRebNumber(),
      project.getRebExpiry(),
      project.getDescription(),
      toStringOrNull(project.getSamplesExpected()),
      project.getContactName(),
      project.getContactEmail()
    };

    return data;
  }
}
