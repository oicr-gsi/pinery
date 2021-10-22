package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.RequisitionDto;
import ca.on.oicr.ws.dto.SignOffDto;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RequisitionWriter extends Writer {

  private static final String[] headers = {"id", "name", "assayId", "sampleIds", "signOffs"};

  private final List<RequisitionDto> requisitions;

  public RequisitionWriter(List<RequisitionDto> requisitions) {
    this.requisitions = requisitions;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return requisitions.size();
  }

  @Override
  protected String[] getRecord(int row) {
    RequisitionDto req = requisitions.get(row);

    return new String[] {
      req.getId().toString(),
      req.getName(),
      Objects.toString(req.getAssayId(), ""),
      getSampleIdsString(req.getSampleIds()),
      getSignOffsString(req.getSignOffs())
    };
  }

  private static String getSampleIdsString(Set<String> sampleIds) {
    if (sampleIds == null || sampleIds.isEmpty()) {
      return "";
    }
    ArrayStringBuilder sb = new ArrayStringBuilder();
    for (String sampleId : sampleIds) {
      sb.append(sampleId);
    }
    return sb.toString();
  }

  private static String getSignOffsString(List<SignOffDto> signOffs) {
    if (signOffs == null || signOffs.isEmpty()) {
      return "";
    }
    ArrayStringBuilder sb = new ArrayStringBuilder();
    for (SignOffDto signOff : signOffs) {
      sb.append(getSignOffString(signOff));
    }
    return sb.toString();
  }

  private static String getSignOffString(SignOffDto signOff) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    sb.append("name", signOff.getName());
    sb.append("passed", signOff.getPassed());
    sb.append("date", signOff.getDate());
    sb.append("userId", signOff.getUserId());
    return sb.toString();
  }
}
