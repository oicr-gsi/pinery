package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;

public interface SampleDao {
  
  /**
   * Retrieves a single Sample by Sample ID
   * 
   * @param id ID of the Sample to retrieve
   * @return the Sample if one is found with the provided Sample ID; otherwise null
   */
  public Sample getSample(Integer id);
  
  /**
   * Retrieves a filtered List of all Samples
   * 
   * @param archived archive status to filter by (null for none)
   * @param projects Projects to include Samples for (null or empty for all)
   * @param types Sample Types to include (null or empty for all)
   * @param before latest Sample creation date for Samples to include (null for any)
   * @param after earliest Sample creation date for Samples to include (null for any)
   * @return a List of all Samples matching the provided criteria (all samples if all parameters are null)
   */
  public List<Sample> getSamplesFiltered(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after);
  
  /**
   * @return a List of all Projects which have Samples
   */
  public List<SampleProject> getAllSampleProjects();
  
  /**
   * @return a List of all optional Sample Attributes
   */
  public List<AttributeName> getAllSampleAttributes();
  
  /**
   * @return a List of all Sample Types
   */
  public List<Type> getAllSampleTypes();
  
}
