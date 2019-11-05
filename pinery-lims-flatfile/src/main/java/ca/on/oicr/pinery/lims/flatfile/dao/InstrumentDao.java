package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import java.util.List;

public interface InstrumentDao {

  /** @return a List of all Instruments */
  public List<Instrument> getAllInstruments();

  /**
   * Retrieves a single Instrument by Instrument ID
   *
   * @param id ID if the Instrument to retrieve
   * @return the Instrument if one is found with the provided Instrument ID; otherwise null
   */
  public Instrument getInstrument(Integer id);

  /** @return a List of all Instrument Models */
  public List<InstrumentModel> getAllInstrumentModels();

  /**
   * Retrieves a single Instrument Model by Instrument Model ID
   *
   * @param id ID of the Instrument Model to retrieve
   * @return the Instrument Model if one is found with the provided Instrument Model ID; otherwise
   *     null
   */
  public InstrumentModel getInstrumentModel(Integer id);

  /**
   * Retrieves all Instruments of a particular Instrument Model
   *
   * @param modelId ID of the Instrument Model
   * @return a List of all Instruments with the provided Instrument Model ID
   */
  public List<Instrument> getInstrumentModelInstruments(Integer modelId);
}
