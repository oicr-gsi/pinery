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
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.lims.DefaultSample;

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

//			CSVReader reader = new CSVReader(br, '\t');
//			String[] nextLine;
//			while ((nextLine = reader.readNext()) != null) {
//				System.out.println(nextLine[0] + " " + nextLine[2] + " " + nextLine[10]);
//			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	List<Sample> getSamples(Reader reader) {
		CSVReader csvReader = new CSVReader(reader, '\t');
//		HeaderColumnNameTranslateMappingStrategy<DefaultSample> strat = new HeaderColumnNameTranslateMappingStrategy<DefaultSample>();
		HeaderColumnNameMappingStrategy<DefaultSample> strat = new HeaderColumnNameMappingStrategy<DefaultSample>();
		strat.setType(DefaultSample.class);
		Map<String, String> map = Maps.newHashMap();
		map.put("template_id", "id");
		map.put("name", "name");
//		strat.setColumnMapping(map);
//		strat.
		
		CsvToBean<DefaultSample> csvToBean = new CsvToBean<DefaultSample>();
		List<DefaultSample> defaultSamples = csvToBean.parse(strat, csvReader);
		List<Sample> samples = Lists.newArrayList();
		for(Sample defaultSample : defaultSamples) {
			samples.add(defaultSample);
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

}
