package ca.on.oicr.pinery.lims.gsle;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger log = LoggerFactory.getLogger(GsleClient.class);

	public final static String UTF8 = "UTF8";

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

//	@Override
//	public List<Sample> getSamples() {
//		// log.error("Inside getSamples");
//		try {
//			ClientRequest request = new ClientRequest("http://" + url + "/SQLApi?key=" + key + ";id=15887;header=1");
//			// log.error("The uri is [{}].", request.getUri());
//			request.accept("text/plain");
//			ClientResponse<String> response = request.get(String.class);
//
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//			}
//			// log.error("** getSample: \n{}", response.getEntity());
//			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity()
//					.getBytes(UTF8)), UTF8));
//			return getSamples(br);
//
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace(System.out);
//		}
//		return null;
//	}
	
	private List<Sample> getSamples() {
		return getSamples(null, null, null, null, null);
	}

	@Override
	public List<Sample> getSamples(Boolean archived, Set<String> projects, Set<String> types, DateTime before,
			DateTime after) {
		if (before == null) {
			before = DateTime.now().plusDays(1);
		}
		if (after == null) {
			after = DateTime.now().withYear(2005);
		}
		// log.error("Inside getSamples");
		StringBuilder sb = getBaseUrl("74209");
		sb.append(getArchivedSqlString(archived));
		// sb.append(";bind=OVCA_%|ACC_%");
		if (projects != null && !projects.isEmpty()) {
			sb.append(getSetSqlString(projects, "_%"));
		} else {
			sb.append(";bind=%");
		}
		if(types != null && !types.isEmpty()) {
			sb.append(getSetSqlString(types, null));
		} else {
			sb.append(";bind=%");
		}
		sb.append(getDateSqlString(after));
		sb.append(getDateSqlString(before));
		log.error("Samples url [{}].", sb.toString());
		try {
			ClientRequest request = new ClientRequest(sb.toString());
			// log.error("The uri is [{}].", request.getUri());
			request.accept("text/plain");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			// log.error("** getSample: \n{}", response.getEntity());
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity()
					.getBytes(UTF8)), UTF8));
			return getSamples(br);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace(System.out);
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
		map.put("tube_barcode","tubeBarcode");
		map.put("volume","volume");
		map.put("concentration","concentration");
		map.put("storage_location","storageLocation");
		map.put("prep_kit_name", "prepKitName");
		map.put("prep_kit_description", "prepKitDescription");
		strat.setColumnMapping(map);

		CsvToBean<GsleSample> csvToBean = new CsvToBean<GsleSample>();
		List<GsleSample> defaultSamples = csvToBean.parse(strat, csvReader);
		List<Sample> samples = Lists.newArrayList();
		for (Sample defaultSample : defaultSamples) {
			samples.add(defaultSample);
			// System.out.println("*** " + defaultSample.getName() + " [" +
			// defaultSample.getCreated() + "] [" + defaultSample.getModified()
			// + "] " + defaultSample.getDescription() + " isArchived[" +
			// defaultSample.getArchived() + "]");
		}
		System.out.println("---- Missing dates ----");
		for (Sample foo : samples) {
			if (foo.getModified() == null || foo.getCreated() == null) {
				System.out.println("*** " + foo.getName() + " [" + foo.getCreated() + "] [" + foo.getModified() + "] "
						+ foo.getDescription() + " isArchived[" + foo.getArchived() + "]");
			}
		}
		return samples;
	}

	@Override
	public Sample getSample(Integer id) {
		// log.error("Inside getSample with id [{}]", id);
		try {
			ClientRequest request = new ClientRequest("http://" + url + "/SQLApi?key=" + key
					+ ";id=15888;header=1;bind=" + id);
			request.accept("text/plain");
			// log.error("The uri is [{}].", request.getUri());
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			// log.error("** getSample: \n{}", response.getEntity());
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity()
					.getBytes(UTF8)), UTF8));

			List<Sample> samples = getSamples(br);
			if (samples.size() == 1) {
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
		for (Sample sample : samples) {
			SampleProject project = projectMap.get(sample.getProject());
			if (project == null) {
				project = new DefaultSampleProject();
				project.setName(sample.getProject());
				project.setCount(1);
				project.setEarliest(sample.getCreated());
				project.setLatest(sample.getModified());
				if (sample.getArchived()) {
					project.setArchivedCount(1);
				}
				projectMap.put(sample.getProject(), project);
			} else {
				project.setCount(project.getCount() + 1);
				if (sample.getArchived()) {
					project.setArchivedCount(project.getArchivedCount() + 1);
				}
				if (sample.getCreated() != null && project.getEarliest() != null
						&& sample.getCreated().before(project.getEarliest())) {
					project.setEarliest(sample.getCreated());
				}
				if (sample.getModified() != null && project.getLatest() != null
						&& sample.getModified().after(project.getLatest())) {
					project.setLatest(sample.getModified());
				}
			}
		}

		List<SampleProject> result = Lists.newArrayList(projectMap.values());
		return result;
	}

	StringBuilder getBaseUrl(String sqlApiQueryId) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(url);
		sb.append("/SQLApi?key=");
		sb.append(key);
		sb.append(";id=");
		sb.append(sqlApiQueryId);
		sb.append(";header=1");
		return sb;
	}

	String getArchivedSqlString(Boolean archived) {
		if (archived == null) {
			return ";bind=0;bind=1";
		}
		if (archived) {
			return ";bind=1;bind=1";
		} else {
			return ";bind=0;bind=0";
		}
	}

	String getDateSqlString(DateTime date) {
		try {
			return ";bind=" + URLEncoder.encode(GsleSample.dateTimeFormatter.print(date), "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	String getSetSqlString(Set<String> set, String postscript) {
		StringBuilder sb = new StringBuilder();
		for (String item : set) {
			try {
				sb.append(URLEncoder.encode(item, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if (postscript != null) {
				sb.append(postscript);
			}
			sb.append('|');
		}
		sb.setLength(sb.length() - 1);
		return ";bind=" + sb.toString();
	}

}
