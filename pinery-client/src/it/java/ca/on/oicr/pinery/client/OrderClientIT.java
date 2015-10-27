package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

public class OrderClientIT {
	
	private static PineryClient pinery;
	
	private static Integer KNOWN_ORDER_ID;
	private static String KNOWN_ORDER_PROJECT;
	private static String KNOWN_ORDER_PLATFORM;
	private static Integer KNOWN_ORDER_SAMPLE_ID;
	private static String KNOWN_ORDER_SAMPLE_ATTRIBUTE_NAME;
	private static String KNOWN_ORDER_SAMPLE_ATTRIBUTE_VALUE;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_ORDER_ID = props.getInt("it.order.id");
    KNOWN_ORDER_PROJECT = props.get("it.order.project");
    KNOWN_ORDER_PLATFORM = props.get("it.order.platform");
    KNOWN_ORDER_SAMPLE_ID = props.getInt("it.order.sampleId");
    KNOWN_ORDER_SAMPLE_ATTRIBUTE_NAME = props.get("it.order.sample.attribute.name");
    KNOWN_ORDER_SAMPLE_ATTRIBUTE_VALUE = props.get("it.order.sample.attribute.value");
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
