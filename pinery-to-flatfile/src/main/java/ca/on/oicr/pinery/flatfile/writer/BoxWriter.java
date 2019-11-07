package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.BoxDto;
import ca.on.oicr.ws.dto.BoxPositionDto;
import java.util.List;
import java.util.Objects;

public class BoxWriter extends Writer {

  private static final String[] headers = {
    "id", "name", "description", "location", "rows", "columns", "samples"
  };

  private final List<BoxDto> boxes;

  public BoxWriter(List<BoxDto> boxes) {
    this.boxes = boxes;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return boxes.size();
  }

  @Override
  protected String[] getRecord(int row) {
    BoxDto box = boxes.get(row);

    String[] data = {
      Objects.toString(box.getId(), ""),
      box.getName(),
      box.getDescription(),
      box.getLocation(),
      Objects.toString(box.getRows(), ""),
      Objects.toString(box.getColumns(), ""),
      getBoxPositionsString(box)
    };

    return data;
  }

  private String getBoxPositionsString(BoxDto box) {
    ArrayStringBuilder sb = new ArrayStringBuilder();
    for (BoxPositionDto pos : box.getPositions()) {
      sb.append(getBoxPositionString(pos));
    }
    return sb.toString();
  }

  private String getBoxPositionString(BoxPositionDto pos) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    sb.append("position", pos.getPosition());
    sb.append("sampleId", pos.getSampleId());
    return sb.toString();
  }
}
