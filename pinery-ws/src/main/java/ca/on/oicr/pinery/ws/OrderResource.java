package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.service.OrderService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.google.common.collect.Lists;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders")
public class OrderResource {

  @Autowired
  private OrderService orderService;

  void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders")
  @Operation(summary = "List all orders")
  public List<OrderDto> getOrders(UriComponentsBuilder uriBuilder) {
    List<Order> orders = orderService.getOrder();
    List<OrderDto> result = Lists.newArrayList();
    for (Order order : orders) {
      OrderDto dto = Dtos.asDto(order);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/order/{id}")
  @Operation(summary = "Find order by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No order found", content = @Content)
  })
  public OrderDto getOrder(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of order to fetch") @PathVariable("id") Integer id) {
    Order order = orderService.getOrder(id);
    if (order == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No order found with ID: " + id);
    }
    OrderDto dto = Dtos.asDto(order);
    addUrls(dto, uriBuilder);
    return dto;
  }

  private void addUrls(OrderDto dto, UriComponentsBuilder uriBuilder) {
    URI baseUri = getBaseUri(uriBuilder);
    dto.setUrl(buildOrderUrl(baseUri, dto.getId()));

    if (dto != null && dto.getSamples() != null) {
      for (OrderDtoSample orderDtoSample : dto.getSamples()) {
        orderDtoSample.setUrl(buildSampleUrl(baseUri, orderDtoSample.getId()));
      }
    }
    if (dto.getCreatedById() != null) {
      dto.setCreatedByUrl(buildUserUrl(baseUri, dto.getCreatedById()));
    }
    if (dto.getModifiedById() != null) {
      dto.setModifiedByUrl(buildUserUrl(baseUri, dto.getModifiedById()));
    }
  }
}
