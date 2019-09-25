package ca.on.oicr.pinery.flatfile.writer;

import static ca.on.oicr.pinery.flatfile.util.ConverterUtils.mapToIds;

import java.util.List;
import java.util.Map;

import ca.on.oicr.pinery.flatfile.util.ConverterUtils.IdGetter;
import ca.on.oicr.ws.dto.InstrumentDto;
import ca.on.oicr.ws.dto.InstrumentModelDto;

public class InstrumentWriter extends Writer {
  
  private static final String[] headers = {
      "id",
      "name",
      "createdDate",
      "modelId",
      "modelName",
      "platform",
      "modelCreatedDate",
      "modelCreatedUserId",
      "modelModifiedDate",
      "modelModifiedUserId",
  };
  
  List<InstrumentDto> instruments;
  Map<Integer, InstrumentModelDto> models;
  
  public InstrumentWriter(List<InstrumentDto> instruments, List<InstrumentModelDto> models) {
    this.instruments = instruments;
    this.models = mapToIds(models, new IdGetter<InstrumentModelDto>() {
      @Override
      public Integer getId(InstrumentModelDto item) {
        return item.getId();
      }
    });
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected String[] getRecord(int row) {
    InstrumentDto instrument = instruments.get(row);
    InstrumentModelDto model = models.get(instrument.getModelId());
    
    String[] data = {
        instrument.getId().toString(),
        instrument.getName(),
        instrument.getCreatedDate(),
        model.getId().toString(),
        model.getName(),
            model.getPlatform(),
        model.getCreatedDate(),
        model.getCreatedById() == null ? "" : model.getCreatedById().toString(),
        model.getModifiedDate(),
        model.getModifiedById() == null ? "" : model.getModifiedById().toString(),
    };
    
    return data;
  }

  @Override
  protected int getRecordCount() {
    return instruments.size();
  }

}
