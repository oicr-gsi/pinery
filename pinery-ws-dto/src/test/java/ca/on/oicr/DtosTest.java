package ca.on.oicr;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.Test;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;

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

   @Test
   public void testOrder4() throws Exception {
      Order input = new DefaultOrder();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date date = new Date();
      input.setCreatedDate(date);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getCreatedDate(), is(sf.format(date)));
   }

   @Test
   public void testOrder6() throws Exception {
      Order input = new DefaultOrder();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date date = new Date();
      input.setModifiedDate(date);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getModifiedDate(), is(sf.format(date)));
   }

   @Test
   public void testOrder7() throws Exception {
      Order input = new DefaultOrder();
      input.setId(22);
      OrderDto output = Dtos.asDto(input);
      assertThat(output.getId(), is(22));
   }

   @Test
   public void testOrder9() throws Exception {
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
   public void testOrder10() throws Exception {
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
   public void testOrderSample_too_OrderDtoSample11() throws Exception {
      OrderSample orderSample = new DefaultOrderSample();
      orderSample.setBarcode("ABC");
      OrderDtoSample orderDtoSample = Dtos.asDto(orderSample);
      assertThat(orderDtoSample.getBarcode(), is(orderSample.getBarcode()));
   }

   @Test
   public void testOrderSample_too_OrderDtoSample12() throws Exception {
      boolean status;
      OrderSample orderSample = new DefaultOrderSample();
      Attribute attribute = new DefaultAttribute();
      Set<Attribute> attributes = Sets.newHashSet();
      attribute.setName("read length");
      attribute.setValue("2x101");
      attributes.add(attribute);
      orderSample.setAttributes(attributes);
      OrderDtoSample orderDtoSample = Dtos.asDto(orderSample);
      orderDtoSample.getAttributes();
      status = attributeContainsName(orderDtoSample.getAttributes(), attribute.getName());

      assertThat(status, is(true));
   }

   @Test
   public void testOrderSample_too_OrderDtoSample13() throws Exception {
      boolean status;
      OrderSample orderSample = new DefaultOrderSample();
      Attribute attribute = new DefaultAttribute();
      Set<Attribute> attributes = Sets.newHashSet();
      attribute.setValue("2x101");
      attributes.add(attribute);
      orderSample.setAttributes(attributes);
      OrderDtoSample orderDtoSample = Dtos.asDto(orderSample);
      orderDtoSample.getAttributes();
      status = attributeContainsValue(orderDtoSample.getAttributes(), attribute.getValue());

      assertThat(status, is(true));
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

   public boolean attributeContainsName(Set<AttributeDto> attributeDto, String attributeName) {

      for (AttributeDto dto : attributeDto) {
         if (dto.getName().equals(attributeName)) {
            return true;
         }
      }
      return false;
   }

   public boolean attributeContainsValue(Set<AttributeDto> attributeDto, String attributeValue) {

      for (AttributeDto dto : attributeDto) {
         if (dto.getValue().equals(attributeValue)) {
            return true;
         }
      }
      return false;
   }

   @Test
   public void testOrderRun1() throws Exception {
      Run input = new DefaultRun();
      input.setState("Complete");
      RunDto output = Dtos.asDto(input);
      assertThat(output.getState(), is("Complete"));
   }

   @Test
   public void testOrderRun2() throws Exception {
      Run input = new DefaultRun();
      input.setName("130906_SN804_0130_AC2D8JACXX");
      RunDto output = Dtos.asDto(input);
      assertThat(output.getName(), is("130906_SN804_0130_AC2D8JACXX"));
   }

   @Test
   public void testOrderRun3() throws Exception {
      Run input = new DefaultRun();
      input.setBarcode("C2D8J");
      RunDto output = Dtos.asDto(input);
      assertThat(output.getBarcode(), is("C2D8J"));
   }

   @Test
   public void testRun5() throws Exception {
      Run input = new DefaultRun();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date date = new Date();
      input.setCreatedDate(date);
      RunDto output = Dtos.asDto(input);
      assertThat(output.getCreatedDate(), is(sf.format(date)));
   }

   @Test
   public void testRun6() throws Exception {
      Run input = new DefaultRun();
      input.setId(22);
      RunDto output = Dtos.asDto(input);
      assertThat(output.getId(), is(22));
   }

   @Test
   public void testRun7() throws Exception {
      Run input = new DefaultRun();
      Set<RunPosition> sample = Sets.newHashSet();
      RunPosition runPosition = new DefaultRunPosition();
      runPosition.setPosition(23);
      sample.add(runPosition);
      input.setSample(sample);
      RunDto output = Dtos.asDto(input);
      assertThat(output.getPositions().iterator().next().getPosition(), is(23));
   }

   @Test
   public void testRunPosition_too_RunDtoPosition_8() throws Exception {
      RunPosition runPosition = new DefaultRunPosition();
      runPosition.setPosition(1);
      RunDtoPosition runDtoPosition = Dtos.asDto(runPosition);
      assertThat(runDtoPosition.getPosition(), is(runPosition.getPosition()));
   }

   @Test
   public void testSetRunPosition_too_RunDtoPosition_9() throws Exception {
      Set<RunPosition> input = Sets.newHashSet();
      RunPosition runPosition = new DefaultRunPosition();
      runPosition.setPosition(12);
      input.add(runPosition);
      Set<RunDtoPosition> output = Dtos.asDto2(input);
      assertThat(output.toArray().length, is(1));
   }

   @Test
   public void testRunPosition_too_RunDtoPosition_10() throws Exception {
      boolean status;
      RunPosition runPosition = new DefaultRunPosition();
      RunSample runSample = new DefaultRunSample();
      Set<RunSample> runSamples = Sets.newHashSet();
      runSample.setBarcode("C2D8J");
      runSamples.add(runSample);
      runPosition.setRunSample(runSamples);
      RunDtoPosition runDtoPosition = Dtos.asDto(runPosition);
      runDtoPosition.getSamples();
      status = RunSampleContainsBarcode(runDtoPosition.getSamples(), runSample.getBarcode());

      assertThat(status, is(true));
   }

   @Test
   public void testRunPosition_too_RunDtoPsotion_11() throws Exception {
      boolean status;
      RunPosition runPosition = new DefaultRunPosition();
      RunSample runSample = new DefaultRunSample();
      Set<RunSample> runSamples = Sets.newHashSet();
      runSample.setUrl("https://pinery.res.oicr.on.ca:8443/pinery/sample/45");
      runSamples.add(runSample);
      runPosition.setRunSample(runSamples);
      RunDtoPosition runDtoPosition = Dtos.asDto(runPosition);
      runDtoPosition.getSamples();
      status = RunSampleContainsUrl(runDtoPosition.getSamples(), runSample.getUrl());

      assertThat(status, is(true));
   }

   @Test
   public void testRunPosition_too_RunDtoPsotion_12() throws Exception {
      boolean status;
      RunPosition runPosition = new DefaultRunPosition();
      RunSample runSample = new DefaultRunSample();
      Set<RunSample> runSamples = Sets.newHashSet();
      runSample.setId(12);
      runSamples.add(runSample);
      runPosition.setRunSample(runSamples);
      RunDtoPosition runDtoPosition = Dtos.asDto(runPosition);
      runDtoPosition.getSamples();
      status = RunSampleContainsId(runDtoPosition.getSamples(), runSample.getId());

      assertThat(status, is(true));
   }

   public boolean RunSampleContainsBarcode(Set<RunDtoSample> runDtoSample, String runSampleBarcode) {

      for (RunDtoSample dto : runDtoSample) {
         if (dto.getBarcode().equals(runSampleBarcode)) {
            return true;
         }
      }
      return false;
   }

   public boolean RunSampleContainsUrl(Set<RunDtoSample> runDtoSample, String runSampleUrl) {

      for (RunDtoSample dto : runDtoSample) {
         if (dto.getUrl().equals(runSampleUrl)) {
            return true;
         }
      }
      return false;
   }

   public boolean RunSampleContainsId(Set<RunDtoSample> runDtoSample, Integer runSampleId) {

      for (RunDtoSample dto : runDtoSample) {
         if (dto.getId() == (runSampleId)) {
            return true;
         }
      }
      return false;
   }

}