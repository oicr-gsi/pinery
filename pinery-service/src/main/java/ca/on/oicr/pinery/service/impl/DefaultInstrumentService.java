package ca.on.oicr.pinery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.service.InstrumentService;

@Service
public class DefaultInstrumentService implements InstrumentService {

   @Autowired
   private Lims lims;

   @Override
   public List<InstrumentModel> getInstrumentModels() {
      return lims.getInstrumentModels();
   }

   @Override
   public InstrumentModel getInstrumentModel(Integer id) {
      return lims.getInstrumentModel(id);
   }

   @Override
   public List<Instrument> getInstruments(Integer instrumentModelId) {
      return lims.getInstruments(instrumentModelId);
   }

   @Override
   public Instrument getInstrument(Integer instrumentModelId, Integer instrumentId) {
      return lims.getInstrument(instrumentModelId, instrumentId);
   }
}
