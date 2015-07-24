package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.OrderDto;

public class OrderClient extends ResourceClient<OrderDto> {

	public OrderClient(PineryClient mainClient) {
		super(OrderDto.class, OrderDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all orders in the database
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<OrderDto> all() throws HttpResponseException {
		return getResourceList("orders");
	}
	
	/**
	 * Retrieves a single order by ID
	 * 
	 * @param orderId LIMS ID of the order to retrieve
	 * @return the order
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public OrderDto byId(int orderId) throws HttpResponseException {
		return getResource("order/"+orderId);
	}

}
