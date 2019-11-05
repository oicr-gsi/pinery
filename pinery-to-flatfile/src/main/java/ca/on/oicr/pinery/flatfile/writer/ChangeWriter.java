package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.ws.dto.ChangeDto;
import ca.on.oicr.ws.dto.ChangeLogDto;
import java.util.ArrayList;
import java.util.List;

public class ChangeWriter extends Writer {

  private static final String[] headers = {"sampleId", "action", "createdDate", "createdUserId"};

  private final List<Change> changes;

  public ChangeWriter(List<ChangeLogDto> changeLogs) {
    ArrayList<Change> changes = new ArrayList<>();

    for (ChangeLogDto changeLog : changeLogs) {
      String sampleId = changeLog.getSampleId();
      for (ChangeDto change : changeLog.getChanges()) {
        changes.add(new Change(sampleId, change));
      }
    }

    this.changes = changes;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return changes.size();
  }

  @Override
  protected String[] getRecord(int row) {
    ChangeDto change = changes.get(row).getChange();

    String[] data = {
      "" + changes.get(row).getSampleId(),
      change.getAction(),
      change.getCreatedDate(),
      change.getCreatedById().toString()
    };

    return data;
  }

  private static class Change {

    private final String sampleId;
    private final ChangeDto change;

    public Change(String sampleId, ChangeDto change) {
      this.sampleId = sampleId;
      this.change = change;
    }

    public String getSampleId() {
      return sampleId;
    }

    public ChangeDto getChange() {
      return change;
    }
  }
}
