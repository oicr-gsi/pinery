package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.SampleDto;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SampleClient extends ResourceClient<SampleDto> {

  private static final String resourceDir = "sample/";

  public SampleClient(PineryClient mainClient) {
    super(SampleDto.class, SampleDto[].class, mainClient);
  }

  /**
   * @return a list of all samples in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<SampleDto> all() throws HttpResponseException {
    return getResourceList("samples");
  }

  /**
   * Retrieves a single sample by ID
   *
   * @param sampleId LIMS ID of the sample to retrieve
   * @return the sample
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public SampleDto byId(String sampleId) throws HttpResponseException {
    return getResource(resourceDir + sampleId);
  }

  /**
   * Retrieves all samples that meet the specified criteria
   *
   * @param filter contains criteria to match
   * @return a list of all samples that meet the criteria specified by the filter
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<SampleDto> allFiltered(SamplesFilter filter) throws HttpResponseException {
    return getResourceList(filter.buildUrl("samples"));
  }

  /**
   * Encapsulates parameters for all samples GET request. All 'with{param}' methods return this to
   * allow chaining calls. Subsequent calls to append the same parameter will overwrite the previous
   * setting.
   *
   * @author dcooke
   */
  public static class SamplesFilter {
    private Boolean archived = null;
    private String beforeDate = null;
    private String afterDate = null;
    private List<String> projects = null;
    private List<String> types = null;

    public SamplesFilter() {}

    public SamplesFilter withArchived(Boolean archived) {
      this.archived = archived;
      return this;
    }

    public SamplesFilter withDateBefore(ZonedDateTime before) {
      this.beforeDate = before.format(DateTimeFormatter.ISO_DATE_TIME);
      return this;
    }

    public SamplesFilter withDateAfter(ZonedDateTime after) {
      this.afterDate = after.format(DateTimeFormatter.ISO_DATE_TIME);
      return this;
    }

    /**
     * Set the projects to find samples of. Will return all samples that match any of the projects
     * (one OR another)
     *
     * @param projects the project names to match
     * @return the same AllSamplesFilter for chaining calls
     */
    public SamplesFilter withProjects(List<String> projects) {
      this.projects = projects;
      return this;
    }

    /**
     * Set the sample types to find samples of. Will return all samples that match any of the types
     * (one OR another)
     *
     * @param types the types to match
     * @return the same AllSamplesFilter for chaining calls
     */
    public SamplesFilter withTypes(List<String> types) {
      this.types = types;
      return this;
    }

    /**
     * Appends all non-null parameters to the resource URL String
     *
     * @param urlWithoutParams the resource URL to add parameters to
     * @return a String composed of the resource URL with all parameters appended
     */
    protected String buildUrl(String urlWithoutParams) {
      StringBuilder sb = new StringBuilder(urlWithoutParams).append("?");

      if (archived != null) {
        sb.append("archived=").append(archived.toString()).append("&");
      }
      if (beforeDate != null) {
        sb.append("before=").append(beforeDate).append("&");
      }
      if (afterDate != null) {
        sb.append("after=").append(afterDate).append("&");
      }
      if (projects != null && !projects.isEmpty()) {
        for (String project : projects) {
          sb.append("project=").append(project).append("&");
        }
      }
      if (types != null && !types.isEmpty()) {
        for (String type : types) {
          sb.append("type=").append(type).append("&");
        }
      }

      sb.deleteCharAt(sb.length() - 1);
      return sb.toString();
    }
  }
}
