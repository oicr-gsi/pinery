package ca.on.oicr.pinery.lims.gsle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.on.oicr.pinery.api.Sample;

public class GsleClientTest {

	private GsleClient sut;

	@Before
	public void setUp() throws Exception {
		sut = new GsleClient();
	}

	@Ignore
	@Test
	public void testGetTemplates() throws Exception {
		List<Sample> samples = sut. getSamples();
		for(Sample sample: samples) {
			System.out.println(sample.getDescription() + "    " + sample.getId());
		}
		assertThat(samples.size(), is(22737));
	}

	@Test
	public void testCsvToSample() throws Exception {
		String s = "template_id\tname\tdescription\n" + "123456\tPCSI_01\tFirst PCSI File\n" + "09878\tMMPC_02\tSecond PCSI File";
//		String s = "123456\tPCSI_01\tFirst PCSI File\n" + "09878\tMMPC_02\tSecond PCSI File";
		List<Sample> samples = sut.getSamples(new StringReader(s));
		assertThat(samples.size(), is(2));
	}

}
