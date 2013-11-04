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

   @Test
   public void test_sample_order_map_with_one_order() throws Exception {
      GsleClient sut = new GsleClient();
      Map<Integer, Set<OrderSample>> map = sut.sampleOrderMap(get_temporary_list_test_input_one());

      assertThat("Number of orders in map", map.size(), is(1));
   }

   @Test
   public void test_sample_order_map_with_two_orders() throws Exception {
      GsleClient sut = new GsleClient();
      Map<Integer, Set<OrderSample>> map = sut.sampleOrderMap(get_temporary_list_test_input_two());

      assertThat("Number of orders in map", map.size(), is(2));
   }

   @Test
   public void test_attribute_order_map_with_five_attributes() throws Exception {
      GsleClient sut = new GsleClient();
      Map<Integer, Set<Attribute>> map = sut.attributeOrderMap(get_temporary_list_test_input_one());

      assertThat("Number of samples in map", map.size(), is(2));
      assertThat("Number of attributes in sample 13312", map.get(13312).size(), is(2));
      assertThat("Number of attributes in sample 13315", map.get(13315).size(), is(3));
   }

   /**
    * A single order containing two samples. One sample with two attributes and
    * another sample with three attributes.
    */
   private List<Temporary> get_temporary_list_test_input_one() {
      List<Temporary> temporaries = Lists.newArrayList();
      Temporary t0 = new Temporary();
      t0.setOrderId(1047);
      t0.setSampleId(13312);
      t0.setName("Lib. Frag. Size (mean, bp)");
      t0.setValue("252");

      Temporary t1 = new Temporary();
      t1.setOrderId(1047);
      t1.setSampleId(13312);
      t1.setName("Read Length");
      t1.setValue("75x35");

      Temporary t2 = new Temporary();
      t2.setOrderId(1047);
      t2.setSampleId(13315);
      t2.setName("Lib. Frag. Size (mean, bp)");
      t2.setValue("246");

      Temporary t3 = new Temporary();
      t3.setOrderId(1047);
      t3.setSampleId(13315);
      t3.setName("Read Length");
      t3.setValue("75x35");

      Temporary t4 = new Temporary();
      t4.setOrderId(1047);
      t4.setSampleId(13315);
      t4.setName("Reference");
      t4.setValue("Human hg19");

      temporaries.add(t0);
      temporaries.add(t1);
      temporaries.add(t2);
      temporaries.add(t3);
      temporaries.add(t4);
      return temporaries;
   }

   /** Two orders. */
   private List<Temporary> get_temporary_list_test_input_two() {
      List<Temporary> temporaries = Lists.newArrayList();
      Temporary t0 = new Temporary();
      t0.setOrderId(2222);
      t0.setSampleId(22111);
      t0.setName("Lib. Frag. Size (mean, bp)");
      t0.setValue("252");

      Temporary t1 = new Temporary();
      t1.setOrderId(3333);
      t1.setSampleId(33111);
      t1.setName("Read Length");
      t1.setValue("75x35");

      temporaries.add(t0);
      temporaries.add(t1);
      return temporaries;
   }
}
