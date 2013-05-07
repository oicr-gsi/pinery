package ca.on.oicr.pinery.service;

import java.util.List;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;

public interface InstrumentService {

   public List<InstrumentModel> getInstrumentModels();

   public InstrumentModel getInstrumentModel(Integer id);

   public List<Instrument> getInstruments(Integer instrumentModelId);

   public Instrument getInstrument(Integer instrumentModelId, Integer instrumentId);
}
