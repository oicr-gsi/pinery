package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.OrderDto;

public class OrderClient extends ResourceClient<OrderDto> {

	public OrderClient(PineryClient mainClient) {
		super(OrderDto.class, OrderDto[].class, mainClient);
	}
	
	public List<OrderDto> all() throws HttpResponseException {
		return getResourceList("orders");
	}
	
	public OrderDto byId(int orderId) throws HttpResponseException {
		return getResource("order/"+orderId);
	}

}
