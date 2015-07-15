package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.SampleDto;

public class SampleClient extends ResourceClient<SampleDto> {
	
	// TODO: investigate why DTOs don't implement ca.on.oicr.pinery.api interfaces (Could return Samples rather than SampleDtos)
	
	private static final String resourceDir = "sample/";
	
	protected SampleClient(PineryClient mainClient) {
		super(SampleDto.class, SampleDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all samples in the database
	 */
	public List<SampleDto> all() {
		return getResourceList("samples");
	}
	
	/**
	 * Retrieve a single sample by ID
	 * 
	 * @param sampleId ID of the sample to retrieve
	 * @return the sample
	 */
	public SampleDto byId(int sampleId) {
		return getResource(resourceDir + sampleId);
	}
	
	/**
	 * Retrieve all samples that meet the specified criteria
	 * 
	 * @param filter contains criteria to match
	 * @return a list of all samples that meet the criteria specified by the filter
	 */
	public List<SampleDto> filteredList(AllSamplesFilter filter) {
		return getResourceList(filter.buildUrl("samples"));
	}
	
	/**
	 * Encapsulates parameters for all samples GET request. All 'with{param}' methods return this 
	 * to allow chaining.
	 * 
	 * @author dcooke
	 *
	 */
	public static class AllSamplesFilter {
		private Boolean archived = null;
		private String beforeDate = null;
		private String afterDate = null;
		private List<String> projects = null;
		private List<String> types = null;
		
		public AllSamplesFilter() {}
		
		public AllSamplesFilter withArchived(Boolean archived) {
			this.archived = archived;
			return this;
		}
		
		public AllSamplesFilter withDateBefore(String beforeDate) { // TODO: take date parameter instead
			this.beforeDate = beforeDate;
			return this;
		}
		
		public AllSamplesFilter withDateAfter(String afterDate) { // TODO: take date parameter instead
			this.afterDate = afterDate;
			return this;
		}
		
		/**
		 * Set the projects to find samples for. Will return all samples that match of the projects 
		 * (one OR another)
		 * 
		 * @param projects the project names to match
		 * @return the same AllSamplesFilter for chaining
		 */
		public AllSamplesFilter withProjects(List<String> projects) {
			this.projects = projects;
			return this;
		}
		
		public AllSamplesFilter withTypes(List<String> types) {
			this.projects = types;
			return this;
		}
		
		/**
		 * Appends all parameters to the resource URL String
		 * 
		 * @param urlWithoutParams the resource URL to add parameters to
		 * @return a String composed of the resource URL with all parameters appended
		 */
		private String buildUrl(String urlWithoutParams) {
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
				for (String project : types) {
					sb.append("types=").append(project).append("&");
				}
			}
			
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
	}
	
}
