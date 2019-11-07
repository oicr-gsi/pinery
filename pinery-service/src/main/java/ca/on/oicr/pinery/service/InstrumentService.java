package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import java.util.List;

public interface InstrumentService {

  public List<InstrumentModel> getInstrumentModels();

  public InstrumentModel getInstrumentModel(Integer id);

  public List<Instrument> getInstruments();

  public Instrument getInstrument(Integer instrumentId);

  public List<Instrument> getInstrumentModelInstrument(Integer id);
}
