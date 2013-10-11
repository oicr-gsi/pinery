package ca.on.oicr;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.Test;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

import com.google.common.collect.Sets;

public class DtosTest {
   @Test
   public void testOrder0() throws Exception {
      Order input = new DefaultOrder();
      input.setStatus("Complete");
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getStatus(), is("Complete"));
   }

   @Test
   public void testOrder1() throws Exception {
      Order input = new DefaultOrder();
      input.setProject("HALT");
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getProject(), is("HALT"));
   }

   @Test
   public void testOrder2() throws Exception {
      Order input = new DefaultOrder();
      input.setPlatform("Illumina HiSeq 2000");
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getPlatform(), is("Illumina HiSeq 2000"));
   }

   // @Test
   // public void testOrder4() throws Exception {
   // Order input = new DefaultOrder();
   // input.setCreatedByUrl("https://pinery.hpc.oicr.on.ca:8443/pinery/user/37");
   // OrderDto output = Dtos.asDto(input);
   // assertThat(output.getCreatedByUrl(),
   // is("https://pinery.hpc.oicr.on.ca:8443/pinery/user/37"));
   // }

   @Test
   public void testOrder5() throws Exception {
      Order input = new DefaultOrder();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date date = new Date();
      input.setCreatedDate(date);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getCreatedDate(), is(sf.format(date)));
   }

   // @Test
   // public void testOrder6() throws Exception {
   // Order input = new DefaultOrder();
   // input.setModifiedByUrl("https://pinery.hpc.oicr.on.ca:8443/pinery/user/37");
   // OrderDto output = Dtos.asDto(input);
   // assertThat(output.getModifiedByUrl(),
   // is("https://pinery.hpc.oicr.on.ca:8443/pinery/user/37"));
   // }

   @Test
   public void testOrder7() throws Exception {
      Order input = new DefaultOrder();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date date = new Date();
      input.setModifiedDate(date);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getModifiedDate(), is(sf.format(date)));
   }

   @Test
   public void testOrder8() throws Exception {
      Order input = new DefaultOrder();
      input.setId(22);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getId(), is(22));
   }

   // @Test
   // public void testOrder9() throws Exception {
   // Order input = new DefaultOrder();
   // input.setUrl("https://pinery.hpc.oicr.on.ca:8443/pinery/order/22");
   // OrderDto output = Dtos.asDto(input);
   // assertThat(output.getUrl(),
   // is("https://pinery.hpc.oicr.on.ca:8443/pinery/order/22"));
   // }

   @Test
   public void testOrder10() throws Exception {
      Order input = new DefaultOrder();
      Set<OrderSample> samples = Sets.newHashSet();
      OrderSample orderSample = new DefaultOrderSample();
      orderSample.setBarcode("ACTGGCCCATG");
      samples.add(orderSample);
      input.setSample(samples);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getSamples().size(), is(1));
   }

   @Test
   public void testOrder11() throws Exception {
      Order input = new DefaultOrder();
      Set<OrderSample> samples = Sets.newHashSet();
      OrderSample orderSample = new DefaultOrderSample();
      orderSample.setBarcode("ACTGGCCCATG");
      samples.add(orderSample);
      input.setSample(samples);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getSamples().iterator().next().getBarcode(), is("ACTGGCCCATG"));
   }

   @Test
   public void testOrderSample_too_OrderDtoSample() throws Exception {
      OrderSample orderSample = new DefaultOrderSample();
      orderSample.setBarcode("ABC");
      OrderDtoSample orderDtoSample = Dtos.asDto(orderSample);
      assertThat(orderDtoSample.getBarcode(), is(orderSample.getBarcode()));

   }

   @Test
   public void testSetOrderSample_too_OrderSample() throws Exception {
      Set<OrderSample> input = Sets.newHashSet();
      OrderSample orderSample = new DefaultOrderSample();
      orderSample.setBarcode("ABC");
      input.add(orderSample);
      Set<OrderDtoSample> output = Dtos.asDto1(input);
      assertThat(output.toArray().length, is(1));
   }
}
