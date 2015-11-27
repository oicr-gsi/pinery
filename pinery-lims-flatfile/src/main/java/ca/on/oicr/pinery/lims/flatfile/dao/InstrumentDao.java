package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.InstrumentModel;

public interface InstrumentDao {
  
  public List<InstrumentModel> getAllInstrumentModels();
  public InstrumentModel getInstrumentModel(Integer id);
  
}
