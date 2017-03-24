package ca.on.oicr.pinery.flatfile.writer;

import java.util.List;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;

public class SequencerRunWriter extends Writer {
  
  private static final String[] headers = {
    "id",
    "name",
    "startDate",
    "completionDate",
    "createdDate",
    "createdUserId",
    "modifiedDate",
    "modifiedUserId",
    "instrumentId",
    "instrumentName",
    "state",
    "barcode",
    "runDirectory",
    "positions"
  };
  
  private final List<RunDto> runs;
  
  public SequencerRunWriter(List<RunDto> runs) {
    this.runs = runs;
  }
  
  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return runs.size();
  }

  @Override
  protected String[] getRecord(int row) {
    RunDto run = runs.get(row);
    
    String[] data = {
        run.getId().toString(),
        run.getName(),
        run.getStartDate(),
        run.getCompletionDate(),
        run.getCreatedDate() == null ? "" : run.getCreatedDate(),
        run.getCreatedById() == null ? "" : run.getCreatedById().toString(),
        run.getModifiedDate() == null ? "" : run.getModifiedDate(),
        run.getModifiedById() == null ? "" : run.getModifiedById().toString(),
        run.getInstrumentId() == null ? "" : run.getInstrumentId().toString(),
        run.getInstrumentName(),
        run.getState(),
        run.getBarcode(),
        run.getRunDirectory(),
        getPositionsString(run)
    };
    
    return data;
  }
  
  private static String getPositionsString(RunDto run) {
    ArrayStringBuilder list = new ArrayStringBuilder();
    
    if (run.getPositions() != null) {
      for (RunDtoPosition pos : run.getPositions()) {
        KeyValueStringBuilder sb = new KeyValueStringBuilder();
        sb.append("position", pos.getPosition().toString());
        sb.append("samples", getPositionSamplesString(pos));
        list.append(sb.toString());
      }
    }
    
    return list.toString();
  }
  
  private static String getPositionSamplesString(RunDtoPosition pos) {
    ArrayStringBuilder sb = new ArrayStringBuilder();
    if (pos.getSamples() != null) {
        for (RunDtoSample sample : pos.getSamples()) {
            sb.append(getPositionSampleString(sample));
        }
    }
    return sb.toString();
  }
  
  private static String getPositionSampleString(RunDtoSample sample) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    
    sb.append("id", sample.getId());
    if (sample.getBarcode() != null) sb.append("barcode", sample.getBarcode());
    if (sample.getBarcodeTwo() != null) sb.append("barcodeTwo", sample.getBarcodeTwo());
    if (sample.getAttributes() != null && !sample.getAttributes().isEmpty()) {
      sb.append("attributes", getPositionSampleAttributesString(sample));
    }
    
    return sb.toString();
  }
  
  private static String getPositionSampleAttributesString(RunDtoSample sample) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    for (AttributeDto att : sample.getAttributes()) {
      sb.append(att.getName(), att.getValue());
    }
    return sb.toString();
  }

}
