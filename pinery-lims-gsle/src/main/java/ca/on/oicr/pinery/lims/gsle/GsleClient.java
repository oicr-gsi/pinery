package ca.on.oicr.pinery.lims.gsle;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.GsleSample;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GsleClient implements Lims {
	
	private String key;
	private String url;
	

	public void setKey(String key) {
		this.key = key;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	@Override
	public List<String> getProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sample> getSamples() {
		try {
			ClientRequest request = new ClientRequest(
					"http://" + url + "/SQLApi?key=" + key + ";id=15887;header=1");
			request.accept("text/plain");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity()
					.getBytes())));
			return getSamples(br);

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	List<Sample> getSamples(Reader reader) {
		CSVReader csvReader = new CSVReader(reader, '\t');
		HeaderColumnNameTranslateMappingStrategy<GsleSample> strat = new HeaderColumnNameTranslateMappingStrategy<GsleSample>();
		strat.setType(GsleSample.class);
		Map<String, String> map = Maps.newHashMap();
		map.put("template_id", "idString");
		map.put("name", "name");
		map.put("description", "description");
		map.put("created_at", "createdString");
		map.put("modified_at", "modifiedString");
		map.put("is_archived", "archivedString");
		strat.setColumnMapping(map);
		
		CsvToBean<GsleSample> csvToBean = new CsvToBean<GsleSample>();
		List<GsleSample> defaultSamples = csvToBean.parse(strat, csvReader);
		List<Sample> samples = Lists.newArrayList();
		for(Sample defaultSample : defaultSamples) {
			samples.add(defaultSample);
//			System.out.println("*** " + defaultSample.getName() + " [" + defaultSample.getCreated() + "] [" + defaultSample.getModified() + "] " + defaultSample.getDescription() + " isArchived[" + defaultSample.getArchived() + "]");
		}
		System.out.println("---- Missing dates ----");
		for( Sample foo : samples) {
			if(foo.getModified() == null || foo.getCreated() == null) {
				System.out.println("*** " + foo.getName() + " [" + foo.getCreated() + "] [" + foo.getModified() + "] " + foo.getDescription() + " isArchived[" + foo.getArchived() + "]");
			}
		}
		return samples;
	}

	@Override
	public Sample getSample(Integer id) {
		try {
			ClientRequest request = new ClientRequest(
					"http://" + url + "/SQLApi?key=" + key + ";id=15888;header=1;bind=" + id);
			request.accept("text/plain");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity()
					.getBytes())));
			
			List<Sample> samples = getSamples(br);
			if(samples.size() == 1) {
				return samples.get(0);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public List<SampleProject> getSampleProjects() {
		List<Sample> samples = getSamples();
		Map<String, SampleProject> projectMap = Maps.newHashMap();
		for(Sample sample : samples) {
			SampleProject project = projectMap.get(sample.getProject());
			if(project == null) {
				project = new  DefaultSampleProject();
				project.setName(sample.getProject());
				project.setCount(1);
				project.setEarliest(sample.getCreated());
				project.setLatest(sample.getModified());
				if(sample.getArchived()) {
					project.setArchivedCount(1);
				}
				projectMap.put(sample.getProject(), project);
			} else {
				project.setCount(project.getCount() + 1);
				if(sample.getArchived()) { project.setArchivedCount(project.getArchivedCount() + 1); }
				if(sample.getCreated() != null && project.getEarliest() != null && sample.getCreated().before(project.getEarliest())) {
					project.setEarliest(sample.getCreated());
				}
				if(sample.getModified() != null && project.getLatest() != null && sample.getModified().after(project.getLatest())) {
					project.setLatest(sample.getModified());
				}
			}
		}
		
		
		List<SampleProject> result = Lists.newArrayList(projectMap.values());
		return result;
	}

}
