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
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.lims.GsleAttribute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

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
   public void sampleOrderMap() throws Exception {

      TemporaryOrder temp = new TemporaryOrder();
      temp.setBarcode("ATCGCCGGA");
      temp.setOrderId(45);
      temp.setSampleId(45);

      TemporaryOrder temp1 = new TemporaryOrder();
      temp1.setBarcode("CCCCCAAATTGG");
      temp1.setOrderId(37);
      temp1.setSampleId(37);

      TemporaryOrder temp2 = new TemporaryOrder();
      temp2.setOrderId(45);
      temp2.setBarcode("GGGGGGGGCGGA");
      temp2.setSampleId(45);

      List<TemporaryOrder> sampleList = Lists.newArrayList();
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
   private List<TemporaryOrder> get_temporary_list_test_input_one() {
      List<TemporaryOrder> temporaries = Lists.newArrayList();
      TemporaryOrder t0 = new TemporaryOrder();
      t0.setOrderId(1047);
      t0.setSampleId(13312);
      t0.setName("Lib. Frag. Size (mean, bp)");
      t0.setValue("252");

      TemporaryOrder t1 = new TemporaryOrder();
      t1.setOrderId(1047);
      t1.setSampleId(13312);
      t1.setName("Read Length");
      t1.setValue("75x35");

      TemporaryOrder t2 = new TemporaryOrder();
      t2.setOrderId(1047);
      t2.setSampleId(13315);
      t2.setName("Lib. Frag. Size (mean, bp)");
      t2.setValue("246");

      TemporaryOrder t3 = new TemporaryOrder();
      t3.setOrderId(1047);
      t3.setSampleId(13315);
      t3.setName("Read Length");
      t3.setValue("75x35");

      TemporaryOrder t4 = new TemporaryOrder();
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
   private List<TemporaryOrder> get_temporary_list_test_input_two() {
      List<TemporaryOrder> temporaries = Lists.newArrayList();
      TemporaryOrder t0 = new TemporaryOrder();
      t0.setOrderId(2222);
      t0.setSampleId(22111);
      t0.setName("Lib. Frag. Size (mean, bp)");
      t0.setValue("252");

      TemporaryOrder t1 = new TemporaryOrder();
      t1.setOrderId(3333);
      t1.setSampleId(33111);
      t1.setName("Read Length");
      t1.setValue("75x35");

      temporaries.add(t0);
      temporaries.add(t1);
      return temporaries;
   }

   // /////////////////////////////////////////////////////////////////////////////////////////
   // /////////////////////////////////////////////////////////////////////////////////////////
   // /////////////////////////////////////////////////////////////////////////////////////////
   // /////////////////////////////////////////////////////////////////////////////////////////
   // ////////////////////////////////////////////////////////////////////////////////////////
   // /////////////////////////////////////////////////////////////////////////////////////////

   @Test
   public void test_sample_Run_map_with_one_Run() throws Exception {
      GsleClient sut = new GsleClient();
      Table<Integer, Integer, Set<RunSample>> table = sut.positionMapGenerator(get_temporary_list_test_input_three());

      assertThat("Number of runs in table", table.size(), is(1));
   }

   @Test
   public void test_sample_run_map_with_two_runs() throws Exception {
      GsleClient sut = new GsleClient();
      Table<Integer, Integer, Set<RunSample>> table = sut.positionMapGenerator(get_temporary_list_test_input_four());

      assertThat("Number of runs in table", table.size(), is(2));
   }

   @Test
   public void test_position_run_map_with_five_positions() throws Exception {
      GsleClient sut = new GsleClient();
      Table<Integer, Integer, Set<RunSample>> table = sut.positionMapGenerator(get_temporary_list_test_input_three());

      assertThat("Number of samples in run 13312", table.get(1047, 1).size(), is(5));
   }

   @Test
   public void test_position_run_map_with_five_positions_2() throws Exception {
      GsleClient sut = new GsleClient();
      Table<Integer, Integer, Set<RunSample>> table = sut.positionMapGenerator(get_temporary_list_test_input_four());

      assertThat("Number of samples in run 13312", table.get(3333, 2).size(), is(1));
   }

   /**
    * A single order containing two samples. One sample with two attributes and
    * another sample with three attributes.
    */
   private List<TemporaryRun> get_temporary_list_test_input_three() {
      List<TemporaryRun> temporaries = Lists.newArrayList();

      TemporaryRun t0 = new TemporaryRun();
      t0.setRunId(1047);
      t0.setSampleId(13312);
      t0.setPosition(1);
      t0.setBarcode("CTGCC");

      TemporaryRun t1 = new TemporaryRun();
      t1.setRunId(1047);
      t1.setSampleId(13312);
      t1.setPosition(1);
      t1.setBarcode("CTGCGC");

      TemporaryRun t2 = new TemporaryRun();
      t2.setRunId(1047);
      t2.setSampleId(13315);
      t2.setPosition(1);
      t2.setBarcode("CTC");

      TemporaryRun t3 = new TemporaryRun();
      t3.setRunId(1047);
      t3.setSampleId(13315);
      t3.setPosition(1);
      t3.setBarcode("TTGC");

      TemporaryRun t4 = new TemporaryRun();
      t4.setRunId(1047);
      t4.setSampleId(13315);
      t4.setPosition(1);
      t4.setBarcode("TGCGGTCT");

      temporaries.add(t0);
      temporaries.add(t1);
      temporaries.add(t2);
      temporaries.add(t3);
      temporaries.add(t4);

      return temporaries;
   }

   /** Two orders. */
   private List<TemporaryRun> get_temporary_list_test_input_four() {
      List<TemporaryRun> temporaries = Lists.newArrayList();
      TemporaryRun t0 = new TemporaryRun();
      t0.setRunId(2222);
      t0.setSampleId(22111);
      t0.setPosition(56);
      t0.setBarcode("TTC");

      TemporaryRun t1 = new TemporaryRun();
      t1.setRunId(3333);
      t1.setSampleId(33111);
      t1.setBarcode("TACG");
      t1.setPosition(2);

      temporaries.add(t0);
      temporaries.add(t1);
      return temporaries;
   }
}
