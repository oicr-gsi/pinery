package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.OrderDto;

public class OrderClient extends ResourceClient<OrderDto> {

	public OrderClient(PineryClient mainClient) {
		super(OrderDto.class, OrderDto[].class, mainClient);
	}
	
	public List<OrderDto> all() {
		return getResourceList("orders");
	}
	
	public OrderDto byId(int orderId) {
		return getResource("order/"+orderId);
	}

}
