package ca.on.oicr.pinery.ws;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunContainer;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunContainer;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.service.OrderService;
import ca.on.oicr.pinery.service.RunService;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoContainer;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

public class ResourceTest {

  private final Date expectedDate = new Date();

  @Test
  public void test_Resource_1() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);

    assertThat(orderDto, is(notNullValue()));
  }

  @Test
  public void test_Resource_2() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    assertThat(orderDto.getCreatedDate(), is(sf.format(expectedDate)));
  }

  @Test
  public void test_Resource_3() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);

    assertThat(orderDto.getId(), is(2));
  }

  @Test
  public void test_Resource_4() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    assertThat(orderDto.getModifiedDate(), is(sf.format(expectedDate)));
  }

  @Test
  public void test_Resource_5() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);

    assertThat(orderDto.getPlatform(), is("Illumina HiSeq 2000"));
  }

  @Test
  public void test_Resource_6() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);

    assertThat(orderDto.getProject(), is("HALT"));
  }

  @Test
  public void test_Resource_7() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder(1)).thenReturn(getOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    OrderDto orderDto = orderResource.getOrder(getUriBuilder(), 1);

    assertThat(orderDto.getStatus(), is("Complete"));
  }

  @Test
  public void test_Resource_8() throws Exception {
    boolean status;
    AttributeDto attributeDto = new AttributeDto();
    Set<AttributeDto> attributeDtoSet = Sets.newHashSet();
    OrderDtoSample orderDtoSample = new OrderDtoSample();
    attributeDto.setName("read length");
    attributeDtoSet.add(attributeDto);
    orderDtoSample.setAttributes(attributeDtoSet);
    status = attributeContainsName(orderDtoSample.getAttributes(), attributeDto.getName());

    assertThat(status, is(true));
  }

  @Test
  public void test_Resource_9() throws Exception {
    boolean status;
    AttributeDto attributeDto = new AttributeDto();
    Set<AttributeDto> attributeDtoSet = Sets.newHashSet();
    OrderDtoSample orderDtoSample = new OrderDtoSample();
    attributeDto.setValue("2x101");
    attributeDtoSet.add(attributeDto);
    orderDtoSample.setAttributes(attributeDtoSet);
    status = attributeContainsValue(orderDtoSample.getAttributes(), attributeDto.getValue());

    assertThat(status, is(true));
  }

  @Test
  public void test_Resource_10() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder()).thenReturn(getListOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);
    List<OrderDto> orderDto = orderResource.getOrders(getUriBuilder());

    assertThat(orderDto, is(notNullValue()));
  }

  @Test
  public void test_Resource_11() throws Exception {
    OrderService orderService = mock(OrderService.class);
    when(orderService.getOrder()).thenReturn(getListOrder());
    OrderResource orderResource = new OrderResource();
    orderResource.setOrderService(orderService);

    List<OrderDto> originalListOrderDto = orderResource.getOrders(getUriBuilder());
    OrderDto orderDto = new OrderDto();
    List<OrderDto> listOrderDto = Lists.newArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    Set<OrderDtoSample> orderDtoSampleSet = Sets.newHashSet();
    OrderDtoSample orderDtoSampleObj = new OrderDtoSample();
    orderDtoSampleObj.setUrl("http://test/sample/2");
    orderDtoSampleSet.add(orderDtoSampleObj);

    orderDto.setCreatedDate(sf.format(expectedDate));
    orderDto.setId(2);
    orderDto.setModifiedDate(sf.format(expectedDate));
    orderDto.setPlatform("Illumina HiSeq 2000");
    orderDto.setProject("HALT");
    orderDto.setStatus("Complete");
    orderDto.setUrl("http://test/order/2");

    for (OrderDtoSample orderDtoSample : orderDtoSampleSet) {
      orderDtoSample.setId("2");
    }

    orderDto.setSamples(orderDtoSampleSet);

    listOrderDto.add(orderDto);

    assertThat(originalListOrderDto, containsInAnyOrder(listOrderDto.toArray()));
  }

  private Order getOrder() {
    Order order = new DefaultOrder();

    Set<OrderSample> orderSampleSet = Sets.newHashSet();
    OrderSample orderSampleObj = new DefaultOrderSample();
    orderSampleSet.add(orderSampleObj);

    order.setCreatedDate(expectedDate);
    order.setId(2);
    order.setModifiedDate(expectedDate);
    order.setPlatform("Illumina HiSeq 2000");
    order.setProject("HALT");
    order.setStatus("Complete");
    for (OrderSample orderSample : orderSampleSet) {
      orderSample.setId("45");
    }
    order.setSample(orderSampleSet);
    return order;
  }

  public boolean attributeContainsName(Set<AttributeDto> attribute, String attributeName) {

    for (AttributeDto dto : attribute) {
      if (dto.getName().equals(attributeName)) {
        return true;
      }
    }
    return false;
  }

  public boolean attributeContainsValue(Set<AttributeDto> attribute, String attributeValue) {

    for (AttributeDto dto : attribute) {
      if (dto.getValue().equals(attributeValue)) {
        return true;
      }
    }
    return false;
  }

  private List<Order> getListOrder() {
    Order order = new DefaultOrder();
    List<Order> list = Lists.newArrayList();

    Set<OrderSample> orderSampleSet = Sets.newHashSet();
    OrderSample orderSampleObj = new DefaultOrderSample();
    orderSampleSet.add(orderSampleObj);

    order.setCreatedDate(expectedDate);
    order.setId(2);
    order.setModifiedDate(expectedDate);
    order.setPlatform("Illumina HiSeq 2000");
    order.setProject("HALT");
    order.setStatus("Complete");

    for (OrderSample orderSample : orderSampleSet) {
      orderSample.setId("2");
    }
    order.setSample(orderSampleSet);

    list.add(order);

    return list;
  }

  @Test
  public void test_Resource__Run_1() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);

    assertThat(runDto, is(notNullValue()));
  }

  @Test
  public void test_Resource__Run_2() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    assertThat(runDto.getCreatedDate(), is(sf.format(expectedDate)));
  }

  @Test
  public void test_Resource_Run_3() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);

    assertThat(runDto.getId(), is(2));
  }

  @Test
  public void test_Resource_Run_4() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);

    assertThat(runDto.getState(), is("Complete"));
  }

  @Test
  public void test_Resource_Run_5() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);

    assertThat(runDto.getName(), is("130906_SN203_0196_AC2D4DACXX"));
  }

  @Test
  public void test_Resource_Run_6() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getRun(1)).thenReturn(getRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    RunDto runDto = runResource.getRun(getUriBuilder(), 1);

    assertThat(runDto.getBarcode(), is("C2D8J"));
  }

  @Test
  public void test_Resource_Run_7() throws Exception {
    boolean status;
    RunDtoSample runDtoSample = new RunDtoSample();
    Set<RunDtoSample> runDtoSampleSet = Sets.newHashSet();
    RunDtoPosition runDtoPosition = new RunDtoPosition();
    runDtoSample.setBarcode("C2D8J");
    runDtoSampleSet.add(runDtoSample);
    runDtoPosition.setSamples(runDtoSampleSet);
    status = runContainsBarcode(runDtoPosition.getSamples(), runDtoSample.getBarcode());

    assertThat(status, is(true));
  }

  @Test
  public void test_Resource_Run_8() throws Exception {
    boolean status;
    RunDtoSample runDtoSample = new RunDtoSample();
    Set<RunDtoSample> runDtoSampleSet = Sets.newHashSet();
    RunDtoPosition runDtoPosition = new RunDtoPosition();
    runDtoSample.setUrl("https://pinery.res.oicr.on.ca:8443/pinery/sample/45");
    runDtoSampleSet.add(runDtoSample);
    runDtoPosition.setSamples(runDtoSampleSet);
    status = runContainsUrl(runDtoPosition.getSamples(), runDtoSample.getUrl());

    assertThat(status, is(true));
  }

  @Test
  public void test_Resource_Run_9() throws Exception {
    boolean status;
    RunDtoSample runDtoSample = new RunDtoSample();
    Set<RunDtoSample> runDtoSampleSet = Sets.newHashSet();
    RunDtoPosition runDtoPosition = new RunDtoPosition();
    runDtoSample.setId("12");
    runDtoSampleSet.add(runDtoSample);
    runDtoPosition.setSamples(runDtoSampleSet);
    status = runContainsId(runDtoPosition.getSamples(), runDtoSample.getId());

    assertThat(status, is(true));
  }

  @Test
  public void test_Resource_Run_10() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getAll(null)).thenReturn(getListRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);
    List<RunDto> runDto = runResource.getRuns(getUriBuilder(), null);

    assertThat(runDto, is(notNullValue()));
  }

  @Ignore
  public void test_Resource_Run_11() throws Exception {
    RunService runService = mock(RunService.class);
    when(runService.getAll(null)).thenReturn(getListRun());
    RunResource runResource = new RunResource();
    runResource.setRunService(runService);

    List<RunDto> originalListRunDto = runResource.getRuns(getUriBuilder(), null);
    RunDto runDto = new RunDto();
    List<RunDto> listRunDto = Lists.newArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    runDto.setCreatedDate(sf.format(expectedDate));
    runDto.setId(2);
    runDto.setName("130906_SN804_0130_AC2D8JACXX");
    runDto.setBarcode("C2D8J");
    runDto.setState("Complete");
    runDto.setUrl("http://test/run//2");

    RunDtoContainer container = new RunDtoContainer();
    Set<RunDtoContainer> containers = Sets.newHashSet();
    containers.add(container);

    RunDtoPosition position = new RunDtoPosition();
    position.setPosition(54);
    Set<RunDtoPosition> positions = Sets.newHashSet();
    positions.add(position);

    RunDtoSample sample = new RunDtoSample();
    sample.setBarcode("ABC");
    sample.setId("45");
    sample.setUrl("http://test/run/45");
    Set<RunDtoSample> samples = Sets.newHashSet();
    samples.add(sample);

    position.setSamples(samples);
    container.setPositions(positions);
    runDto.setContainers(containers);

    listRunDto.add(runDto);

    assertThat(originalListRunDto, containsInAnyOrder(listRunDto.toArray()));
  }

  private Run getRun() {
    Run run = new DefaultRun();
    run.setCreatedDate(expectedDate);
    run.setId(2);
    run.setBarcode("C2D8J");
    run.setName("130906_SN203_0196_AC2D4DACXX");
    run.setState("Complete");

    RunContainer container = new DefaultRunContainer();
    Set<RunContainer> containers = Sets.newHashSet();
    containers.add(container);

    RunPosition position = new DefaultRunPosition();
    position.setPosition(54);
    Set<RunPosition> positions = Sets.newHashSet();
    positions.add(position);

    RunSample sample = new DefaultRunSample();
    sample.setBarcode("ABC");
    sample.setId("45");
    Set<RunSample> samples = Sets.newHashSet();
    samples.add(sample);

    position.setRunSample(samples);
    container.setPositions(positions);
    run.setContainers(containers);
    return run;
  }

  public boolean runContainsBarcode(Set<RunDtoSample> run, String runBarcode) {

    for (RunDtoSample dto : run) {
      if (dto.getBarcode().equals(runBarcode)) {
        return true;
      }
    }
    return false;
  }

  public boolean runContainsUrl(Set<RunDtoSample> run, String runUrl) {

    for (RunDtoSample dto : run) {
      if (dto.getUrl().equals(runUrl)) {
        return true;
      }
    }
    return false;
  }

  public boolean runContainsId(Set<RunDtoSample> runSamples, String runSampleId) {

    for (RunDtoSample dto : runSamples) {
      if (dto.getId().equals(runSampleId)) {
        return true;
      }
    }
    return false;
  }

  private List<Run> getListRun() {
    Run run = new DefaultRun();
    List<Run> list = Lists.newArrayList();

    run.setCreatedDate(expectedDate);
    run.setId(2);
    run.setName("130906_SN804_0130_AC2D8JACXX");
    run.setBarcode("C2D8J");
    run.setState("Complete");

    RunContainer container = new DefaultRunContainer();
    Set<RunContainer> containers = Sets.newHashSet();
    containers.add(container);

    RunPosition position = new DefaultRunPosition();
    position.setPosition(54);
    Set<RunPosition> positions = Sets.newHashSet();
    positions.add(position);

    RunSample sample = new DefaultRunSample();
    sample.setBarcode("ABC");
    sample.setId("45");
    Set<RunSample> samples = Sets.newHashSet();
    samples.add(sample);

    position.setRunSample(samples);
    container.setPositions(positions);
    run.setContainers(containers);

    list.add(run);

    return list;
  }

  private UriComponentsBuilder getUriBuilder() {
    return UriComponentsBuilder.fromHttpUrl("http://test");
  }
}
