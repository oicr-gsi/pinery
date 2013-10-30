package ca.on.oicr.pinery.lims.gsle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.lims.GsleAttribute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GsleClientTest {

   private GsleClient sut;

   @Before
   public void setUp() throws Exception {
      sut = new GsleClient();
   }

   @Test
   public void testCsvToSample() throws Exception {
      String s = "template_id\tname\tdescription\n" + "123456\tPCSI_01\tFirst PCSI File\n" + "09878\tMMPC_02\tSecond PCSI File";
      List<Sample> samples = sut.getSamples(new StringReader(s));
      assertThat(samples.size(), is(2));
   }

   @Test
   public void testBarCodeFilter() throws Exception {

      Map<String, String> map = Maps.newHashMap();
      map.put("Agilent_90_CTGGGT", "CTGGGT");
      sut.setBarcodeMap(map);

      Attribute attr = new GsleAttribute();
      attr.setName("Barcode");
      attr.setValue("Agilent_90_CTGGGT");

      attr = sut.barcodeFilter(attr);
      assertThat(attr.getValue(), is("CTGGGT"));
   }

   @Test
   public void testBarCodeFilter2() throws Exception {

      Map<String, String> map = Maps.newHashMap();
      map.put("Agilent_90_CTGGGT", "CTGGGT");
      sut.setBarcodeMap(map);

      Attribute attr = new GsleAttribute();
      attr.setName("FailingBarcode");
      attr.setValue("Agilent_90_CTGGGT");

      attr = sut.barcodeFilter(attr);
      assertThat(attr.getValue(), is("Agilent_90_CTGGGT"));
   }

   @Test
   public void testBarCodeFilter3() throws Exception {

      Map<String, String> map = Maps.newHashMap();
      map.put("Agilent_90_CTGGGT", null);
      sut.setBarcodeMap(map);

      Attribute attr = new GsleAttribute();
      attr.setName("Barcode");
      attr.setValue("Agilent_90_CTGGGT");

      attr = sut.barcodeFilter(attr);
      assertThat(attr.getValue(), is("Agilent_90_CTGGGT"));

   }

   @Test
   public void testBarCodeFilter4() throws Exception {

      Map<String, String> map = Maps.newHashMap();
      map.put("Agilent_90_CTGGGT", null);
      sut.setBarcodeMap(map);

      Attribute attr = new GsleAttribute();
      attr.setName("Barcode");
      attr.setValue("Agilent_90_CTGGGT");

      attr = sut.barcodeFilter(attr);

      assertThat(attr.getValue(), is("Agilent_90_CTGGGT"));

   }

   @Test
   public void attributeOrderMap() throws Exception {

      Temporary temp = new Temporary();
      temp.setOrderId(42);
      temp.setName("a");
      temp.setValue("b");

      Temporary temp1 = new Temporary();
      temp1.setOrderId(36);
      temp1.setName("c");
      temp1.setValue("d");

      Temporary temp2 = new Temporary();
      temp2.setOrderId(42);
      temp2.setName("e");
      temp2.setValue("f");

      List<Temporary> sampleList = Lists.newArrayList();
      sampleList.add(temp);
      sampleList.add(temp1);
      sampleList.add(temp2);

      Map<Integer, Set<Attribute>> attMap = Maps.newHashMap();

      GsleClient gsleClient = new GsleClient();
      attMap = gsleClient.attributeOrderMap(sampleList);

      assertThat(attMap.get(temp.getOrderId()).size(), is(2));
   }

   @Test
   public void sampleOrderMap() throws Exception {

      Temporary temp = new Temporary();
      temp.setBarcode("ATCGCCGGA");
      temp.setOrderId(45);
      temp.setSampleId(45);

      Temporary temp1 = new Temporary();
      temp1.setBarcode("CCCCCAAATTGG");
      temp1.setOrderId(37);
      temp1.setSampleId(37);

      Temporary temp2 = new Temporary();
      temp2.setOrderId(45);
      temp2.setBarcode("GGGGGGGGCGGA");
      temp2.setSampleId(45);

      List<Temporary> sampleList = Lists.newArrayList();
      sampleList.add(temp);
      sampleList.add(temp1);
      sampleList.add(temp2);

      Map<Integer, Set<OrderSample>> attMap = Maps.newHashMap();

      GsleClient gsleClient = new GsleClient();
      attMap = gsleClient.sampleOrderMap(sampleList);

      assertThat(attMap.get(temp.getOrderId()).size(), is(2));
   }
}
