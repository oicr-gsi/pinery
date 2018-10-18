package ca.on.oicr.pinery.flatfile.writer;

import java.util.List;

import ca.on.oicr.ws.dto.SampleProjectDto;

public class SampleProjectWriter extends Writer {

  private static final String[] headers = {
    "projectName",
    "active"
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
        Boolean.toString(project.isActive())
    };
    
    return data;
  }
  
}
