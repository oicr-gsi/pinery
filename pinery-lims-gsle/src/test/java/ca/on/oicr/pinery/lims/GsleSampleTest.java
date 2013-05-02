package ca.on.oicr.pinery.lims;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.Before;
import org.junit.Test;

public class GsleSampleTest {
	
	private GsleSample sut;

	@Before
	public void setUp() throws Exception {
		sut = new GsleSample();
	}


	
	@Test
	public void testPCSI() throws Exception {
		sut.setName("PCSI_0074_Pa_P_nn_1-1_D_S3");
		assertThat(sut.getProject(), is("PCSI"));
	}

	@Test
	public void testOBC() throws Exception {
		sut.setName("OBC_0003_nn_R_nn_2_D");
		assertThat(sut.getProject(), is("OBC"));
	}
	
	@Test
	public void testM4_is_NONE() throws Exception {
		sut.setName("M4");
		assertThat(sut.getProject(), is(GsleSample.NONE));
	}
	
	@Test
	public void testIsoDate() throws Exception {
		sut.setCreatedString("2012-06-12 14:47:09-04");
	}
	
	@Test
	public void testCustomGsleFormat() throws Exception {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder()
        .appendYear(4, 4)
        .appendLiteral('-')
        .appendMonthOfYear(2)
        .appendLiteral('-')
        .appendDayOfMonth(2)
        .appendLiteral(' ')
        .appendHourOfDay(2)
        .appendLiteral(':')
        .appendMinuteOfHour(2)
        .appendLiteral(':')
        .appendSecondOfMinute(2)
        .appendTimeZoneOffset(null, true, 1, 1)
        .toFormatter();
		DateTime dt = fmt.parseDateTime("2012-06-12 21:47:09-04");
		System.out.println("Custom format " + dt.toString() + "    " + fmt.print(dt));
	}

}
