package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

public class OrderClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final Integer KNOWN_ORDER_ID = 2306;
	private static final String KNOWN_ORDER_PROJECT = "EsophagealAdenocarcinoma";
	private static final String KNOWN_ORDER_PLATFORM = "Illumina HiSeq 2000";
	private static final Integer KNOWN_ORDER_SAMPLE_ID = 57667;
	private static final String KNOWN_ORDER_SAMPLE_ATTRIBUTE_NAME = "Reference";
	private static final String KNOWN_ORDER_SAMPLE_ATTRIBUTE_VALUE = "Human hg19 random";

	public OrderClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getById() throws HttpResponseException {
		OrderDto order = pinery.getOrder().byId(KNOWN_ORDER_ID);
		assertIsKnownOrder(order);
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<OrderDto> orders = pinery.getOrder().all();
		assertTrue(orders.size() > 1);
		boolean orderFound = false;
		for (OrderDto order : orders) {
			if (KNOWN_ORDER_ID.equals(order.getId())) {
				orderFound = true;
				assertIsKnownOrder(order);
				break;
			}
		}
		assertTrue(orderFound);
	}
	
	private void assertIsKnownOrder(OrderDto order) {
		assertEquals(KNOWN_ORDER_ID, order.getId());
		assertEquals(KNOWN_ORDER_PROJECT, order.getProject());
		assertEquals(KNOWN_ORDER_PLATFORM, order.getPlatform());
		Set<OrderDtoSample> samples = order.getSamples();
		boolean sampleAttributeFound = false;
		for (OrderDtoSample sample : samples) {
			if (KNOWN_ORDER_SAMPLE_ID.equals(sample.getId())) {
				Set<AttributeDto> attributes = sample.getAttributes();
				for (AttributeDto attribute : attributes) {
					if (KNOWN_ORDER_SAMPLE_ATTRIBUTE_NAME.equals(attribute.getName())) {
						sampleAttributeFound = true;
						assertEquals(KNOWN_ORDER_SAMPLE_ATTRIBUTE_VALUE, attribute.getValue());
						break;
					}
				}
				break;
			}
		}
		assertTrue(sampleAttributeFound);
	}

}
