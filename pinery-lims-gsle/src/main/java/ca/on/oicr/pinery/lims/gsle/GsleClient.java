package ca.on.oicr.pinery.lims.gsle;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultAttributeName;
import ca.on.oicr.pinery.lims.DefaultChangeLog;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultType;
import ca.on.oicr.pinery.lims.GsleAttribute;
import ca.on.oicr.pinery.lims.GsleChange;
import ca.on.oicr.pinery.lims.GsleInstrument;
import ca.on.oicr.pinery.lims.GsleInstrumentModel;
import ca.on.oicr.pinery.lims.GsleOrder;
import ca.on.oicr.pinery.lims.GsleOrderSample;
import ca.on.oicr.pinery.lims.GsleSample;
import ca.on.oicr.pinery.lims.GsleSampleChildren;
import ca.on.oicr.pinery.lims.GsleSampleParents;
import ca.on.oicr.pinery.lims.GsleUser;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class GsleClient implements Lims {

   // Constructing new map
   private Map<String, String> barcodeMap = Maps.newHashMap();

   private static final Logger log = LoggerFactory.getLogger(GsleClient.class);

   public final static String UTF8 = "UTF8";

   private String key;
   private String url;

   public void setKey(String key) {
      this.key = key;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public List<String> getProjects() {
      // TODO Auto-generated method stub
      return null;
   }

   private List<Sample> getSamples() {
      return getSamples(null, null, null, null, null);
   }

   private Map<Integer, Set<Attribute>> getAttributes() {

      Map<Integer, Set<Attribute>> result = Maps.newHashMap();

      StringBuilder url = getBaseUrl("74401");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getAttributes(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }

      return result;
   }

   public void getBarcode() {

      StringBuilder url = getBaseUrl("174948");

      try {
         ClientRequest request = new ClientRequest(url.toString());
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));

         CSVReader reader = new CSVReader(new BufferedReader(br), '\t');
         String[] nextLine;

         while ((nextLine = reader.readNext()) != null) {
            barcodeMap.put(nextLine[0], nextLine[1]);
         }
      }

      catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
   }

   private Map<Integer, Set<Attribute>> getAttributes(Reader reader) {
      Map<Integer, Set<Attribute>> result = Maps.newHashMap();

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleAttribute> strat = new HeaderColumnNameTranslateMappingStrategy<GsleAttribute>();
      strat.setType(GsleAttribute.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("template_id", "idString");
      map.put("display_label", "name");
      map.put("value", "value");
      strat.setColumnMapping(map);

      CsvToBean<GsleAttribute> csvToBean = new CsvToBean<GsleAttribute>();
      List<GsleAttribute> defaultAttributes = csvToBean.parse(strat, csvReader);
      for (Attribute attribute : defaultAttributes) {
         if (!result.containsKey(attribute.getId())) {
            result.put(attribute.getId(), Sets.<Attribute> newHashSet());
         }
         // Calling barcodeFilter method
         result.get(attribute.getId()).add(barcodeFilter(attribute));
      }
      return result;
   }

   private Map<Integer, Set<Integer>> getChildren() {
      Map<Integer, Set<Integer>> result = Maps.newHashMap();

      StringBuilder url = getBaseUrl("74419");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getChildren(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   private Map<Integer, Set<Integer>> getChildren(Reader reader) {
      Map<Integer, Set<Integer>> result = Maps.newHashMap();

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleSampleChildren> strat = new HeaderColumnNameTranslateMappingStrategy<GsleSampleChildren>();
      strat.setType(GsleSampleChildren.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("parent_id", "parentString");
      map.put("template_id", "childString");
      strat.setColumnMapping(map);

      CsvToBean<GsleSampleChildren> csvToBean = new CsvToBean<GsleSampleChildren>();
      List<GsleSampleChildren> defaultAttributes = csvToBean.parse(strat, csvReader);
      for (GsleSampleChildren attribute : defaultAttributes) {
         if (!result.containsKey(attribute.getParent())) {
            result.put(attribute.getParent(), Sets.<Integer> newHashSet());
         }
         result.get(attribute.getParent()).add(attribute.getChild());
      }
      return result;
   }

   private Map<Integer, Set<Integer>> getParents() {
      Map<Integer, Set<Integer>> result = Maps.newHashMap();

      StringBuilder url = getBaseUrl("74419");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getParents(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   private Map<Integer, Set<Integer>> getParents(Reader reader) {
      Map<Integer, Set<Integer>> result = Maps.newHashMap();

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleSampleParents> strat = new HeaderColumnNameTranslateMappingStrategy<GsleSampleParents>();
      strat.setType(GsleSampleParents.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("template_id", "templateString");
      map.put("parent_id", "parentString");
      strat.setColumnMapping(map);

      CsvToBean<GsleSampleParents> csvToBean = new CsvToBean<GsleSampleParents>();
      List<GsleSampleParents> defaultAttributes = csvToBean.parse(strat, csvReader);
      for (GsleSampleParents attribute : defaultAttributes) {
         if (!result.containsKey(attribute.getTemplate())) {
            result.put(attribute.getTemplate(), Sets.<Integer> newHashSet());
         }
         result.get(attribute.getTemplate()).add(attribute.getParent());
      }
      return result;
   }

   private List<Sample> addAttributes(List<Sample> samples) {
      Map<Integer, Set<Attribute>> attributes = getAttributes();
      for (Sample sample : samples) {
         sample.setAttributes(attributes.get(sample.getId()));
      }
      return samples;
   }

   private List<Sample> addChildren(List<Sample> samples) {
      Map<Integer, Set<Integer>> children = getChildren();
      for (Sample sample : samples) {
         sample.setChildren(children.get(sample.getId()));
      }
      return samples;
   }

   private List<Sample> addParents(List<Sample> samples) {
      Map<Integer, Set<Integer>> parents = getParents();
      for (Sample sample : samples) {
         sample.setParents(parents.get(sample.getId()));
      }
      return samples;
   }

   @Override
   public List<Sample> getSamples(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after) {
      if (before == null) {
         before = DateTime.now().plusDays(1);
      }
      if (after == null) {
         after = DateTime.now().withYear(2005);
      }
      // log.error("Inside getSamples");
      StringBuilder sb = getBaseUrl("74209");
      sb.append(getArchivedSqlString(archived));
      // sb.append(";bind=OVCA_%|ACC_%");
      if (projects != null && !projects.isEmpty()) {
         sb.append(getSetSqlString(projects, "_%"));
      } else {
         sb.append(";bind=%");
      }
      if (types != null && !types.isEmpty()) {
         sb.append(getSetSqlString(types, null));
      } else {
         sb.append(";bind=%");
      }
      sb.append(getDateSqlString(after));
      sb.append(getDateSqlString(before));
      log.error("Samples url [{}].", sb.toString());
      try {
         ClientRequest request = new ClientRequest(sb.toString());
         // log.error("The uri is [{}].", request.getUri());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         // log.error("** getSample: \n{}", response.getEntity());
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         return getSamples(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return null;
   }

   List<Sample> getSamples(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleSample> strat = new HeaderColumnNameTranslateMappingStrategy<GsleSample>();
      strat.setType(GsleSample.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("template_id", "idString");
      map.put("name", "name");
      map.put("description", "description");
      map.put("created_at", "createdString");
      map.put("created_by", "createdByIdString");
      map.put("modified_at", "modifiedString");
      map.put("modified_by", "modifiedByIdString");
      map.put("is_archived", "archivedString");
      map.put("tube_barcode", "tubeBarcode");
      map.put("volume", "volumeString");
      map.put("concentration", "concentrationString");
      map.put("storage_location", "storageLocation");
      map.put("prep_kit_name", "prepKitName");
      map.put("prep_kit_description", "prepKitDescription");
      map.put("status", "statusString");
      map.put("state", "stateString");
      map.put("type_name", "sampleType");
      strat.setColumnMapping(map);

      CsvToBean<GsleSample> csvToBean = new CsvToBean<GsleSample>();
      List<GsleSample> defaultSamples = csvToBean.parse(strat, csvReader);
      List<Sample> samples = Lists.newArrayList();
      for (Sample defaultSample : defaultSamples) {
         samples.add(defaultSample);

         // System.out.println("*** " + defaultSample.getName() + " [" +
         // defaultSample.getCreated() + "] [" + defaultSample.getModified()
         // + "] " + defaultSample.getDescription() + " isArchived[" +
         // defaultSample.getArchived() + "]");
      }

      getBarcode(); // Populating map
      samples = addAttributes(samples);
      barcodeMap.clear(); // Clearing map
      samples = addChildren(samples);
      samples = addParents(samples);
      System.out.println("---- Missing dates ----");
      for (Sample foo : samples) {
         if (foo.getModified() == null || foo.getCreated() == null) {
            System.out.println("*** " + foo.getName() + " [" + foo.getCreated() + "] [" + foo.getModified() + "] " + foo.getDescription()
                  + " isArchived[" + foo.getArchived() + "]");
         }
      }
      return samples;
   }

   @Override
   public Sample getSample(Integer id) {

      // log.error("Inside getSample with id [{}]", id);
      try {
         ClientRequest request = new ClientRequest("http://" + url + "/SQLApi?key=" + key + ";id=74399;header=1;bind=" + id);
         // + ";id=15888;header=1;bind=" + id);
         request.accept("text/plain");
         // log.error("The uri is [{}].", request.getUri());
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         // log.error("** getSample: \n{}", response.getEntity());
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));

         List<Sample> samples = getSamples(br);
         if (samples.size() == 1) {
            return samples.get(0);
         }
      } catch (Exception e) {
         System.out.println(e);
      }
      return null;
   }

   @Override
   public List<SampleProject> getSampleProjects() {
      List<Sample> samples = getSamples();
      Map<String, SampleProject> projectMap = Maps.newHashMap();
      for (Sample sample : samples) {
         SampleProject project = projectMap.get(sample.getProject());
         if (project == null) {
            project = new DefaultSampleProject();
            project.setName(sample.getProject());
            project.setCount(1);
            project.setEarliest(sample.getCreated());
            project.setLatest(sample.getModified());
            if (sample.getArchived()) {
               project.setArchivedCount(1);
            }
            projectMap.put(sample.getProject(), project);
         } else {
            project.setCount(project.getCount() + 1);
            if (sample.getArchived()) {
               project.setArchivedCount(project.getArchivedCount() + 1);
            }
            if (sample.getCreated() != null && project.getEarliest() != null && sample.getCreated().before(project.getEarliest())) {
               project.setEarliest(sample.getCreated());
            }
            if (sample.getModified() != null && project.getLatest() != null && sample.getModified().after(project.getLatest())) {
               project.setLatest(sample.getModified());
            }
         }
      }

      List<SampleProject> result = Lists.newArrayList(projectMap.values());
      return result;
   }

   @Override
   public List<Type> getTypes() {
      List<Sample> samples = getSamples();
      Map<String, Type> typeMap = Maps.newHashMap();
      for (Sample sample : samples) {
         if (!StringUtils.isBlank(sample.getSampleType())) {
            Type type = typeMap.get(sample.getSampleType());
            if (type == null) {
               type = new DefaultType();
               type.setName(sample.getSampleType());
               type.setCount(1);
               type.setEarliest(sample.getCreated());
               type.setLatest(sample.getModified());
               if (sample.getArchived()) {
                  type.setArchivedCount(1);
               }
               typeMap.put(sample.getSampleType(), type);
            } else {
               type.setCount(type.getCount() + 1);
               if (sample.getArchived()) {
                  type.setArchivedCount(type.getArchivedCount() + 1);
               }
               if (sample.getCreated() != null && type.getEarliest() != null && sample.getCreated().before(type.getEarliest())) {
                  type.setEarliest(sample.getCreated());
               }
               if (sample.getModified() != null && type.getLatest() != null && sample.getModified().after(type.getLatest())) {
                  type.setLatest(sample.getModified());
               }
            }
         }
      }

      List<Type> result = Lists.newArrayList(typeMap.values());
      return result;
   }

   @Override
   public List<AttributeName> getAttributeNames() {
      List<Sample> samples = getSamples();
      Map<String, AttributeName> attributeNameMap = Maps.newHashMap();
      for (Sample sample : samples) {
         if (sample.getAttributes() != null) {
            for (Attribute attribute : sample.getAttributes()) {
               AttributeName attributeName = attributeNameMap.get(attribute.getName());
               if (attributeName == null) {
                  attributeName = new DefaultAttributeName();
                  attributeName.setName(attribute.getName());
                  attributeName.setCount(1);
                  attributeName.setEarliest(sample.getCreated());
                  attributeName.setLatest(sample.getModified());
                  if (sample.getArchived()) {
                     attributeName.setArchivedCount(1);
                  }
                  attributeNameMap.put(attribute.getName(), attributeName);
               } else {
                  attributeName.setCount(attributeName.getCount() + 1);
                  if (sample.getArchived()) {
                     attributeName.setArchivedCount(attributeName.getArchivedCount() + 1);
                  }
                  if (sample.getCreated() != null && attributeName.getEarliest() != null
                        && sample.getCreated().before(attributeName.getEarliest())) {
                     attributeName.setEarliest(sample.getCreated());
                  }
                  if (sample.getModified() != null && attributeName.getLatest() != null
                        && sample.getModified().after(attributeName.getLatest())) {
                     attributeName.setLatest(sample.getModified());
                  }
               }
            }
         }
      }

      List<AttributeName> result = Lists.newArrayList(attributeNameMap.values());
      return result;
   }

   StringBuilder getBaseUrl(String sqlApiQueryId) {
      StringBuilder sb = new StringBuilder();
      sb.append("http://");
      sb.append(url);
      sb.append("/SQLApi?key=");
      sb.append(key);
      sb.append(";id=");
      sb.append(sqlApiQueryId);
      sb.append(";header=1");
      return sb;
   }

   String getArchivedSqlString(Boolean archived) {
      if (archived == null) {
         return ";bind=0;bind=1";
      }
      if (archived) {
         return ";bind=1;bind=1";
      } else {
         return ";bind=0;bind=0";
      }
   }

   String getDateSqlString(DateTime date) {
      try {
         return ";bind=" + URLEncoder.encode(GsleSample.dateTimeFormatter.print(date), "UTF8");
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }

   String getSetSqlString(Set<String> set, String postscript) {
      StringBuilder sb = new StringBuilder();
      for (String item : set) {
         try {
            sb.append(URLEncoder.encode(item, "UTF8"));
         } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
         }
         if (postscript != null) {
            sb.append(postscript);
         }
         sb.append('|');
      }
      sb.setLength(sb.length() - 1);
      return ";bind=" + sb.toString();
   }

   @Override
   public List<User> getUsers() {
      List<User> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl("74432");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getUsers(br);
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public User getUser(Integer id) {
      User result = null;

      StringBuilder url = getBaseUrl("74433");
      url.append(";bind=");
      url.append(id);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<User> users = getUsers(br);
         if (users.size() == 1) {
            result = users.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   /*
    * ///////////////////////////////////////////////////////////////////////////
    * GOING TO DELETE
    * 
    * //////////////////////////////////////////////////////////////////////////
    */
   public Set<OrderSample> populateOrder() {

      String mockCsv2 = "id\t" + "barcode\n" + "45\t" + "CTGCTGTG";
      StringReader mockCsvReader2 = new StringReader(mockCsv2);
      List<OrderSample> sample = getOrderSample(mockCsvReader2);

      String mockCsv3 = "name\t" + "value\n" + "read length\t" + "2x101";
      StringReader mockCsv3Reader = new StringReader(mockCsv3);
      List<Attribute> attribute = getAttribute(mockCsv3Reader);
      Set<Attribute> orderSampleAttribute = Sets.newHashSet(attribute);

      OrderSample orderSampleDefault = new DefaultOrderSample();
      orderSampleDefault.setAttributes(orderSampleAttribute);
      sample.add(orderSampleDefault);
      Set<OrderSample> sampleSet = Sets.newHashSet(sample);

      return sampleSet;
   }

   List<Attribute> getAttribute(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleAttribute> strat = new HeaderColumnNameTranslateMappingStrategy<GsleAttribute>();
      strat.setType(GsleAttribute.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("name", "name");
      map.put("value", "value");
      strat.setColumnMapping(map);

      CsvToBean<GsleAttribute> csvToBean = new CsvToBean<GsleAttribute>();
      List<GsleAttribute> defaultAttribute = csvToBean.parse(strat, csvReader);
      List<Attribute> attributeList = Lists.newArrayList();
      for (Attribute attribute : defaultAttribute) {
         attributeList.add(attribute);
      }
      return attributeList;

   }

   List<OrderSample> getOrderSample(Reader reader) {

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleOrderSample> strat = new HeaderColumnNameTranslateMappingStrategy<GsleOrderSample>();
      strat.setType(GsleOrderSample.class);

      Map<String, String> map = Maps.newHashMap();
      map.put("barcode", "barcode");
      map.put("id", "idString");

      strat.setColumnMapping(map);

      CsvToBean<GsleOrderSample> csvToBean = new CsvToBean<GsleOrderSample>();

      List<GsleOrderSample> defaultOrder = csvToBean.parse(strat, csvReader);

      List<OrderSample> samples = Lists.newArrayList();

      for (GsleOrderSample order : defaultOrder) {
         samples.add((OrderSample) order);
      }

      return samples;
   }

   /*
    * ///////////////////////////////////////////////////////////////////////////
    * GOING TO DELETE
    * 
    * //////////////////////////////////////////////////////////////////////////
    */

   List<Order> getOrders(Reader reader) throws SAXException, JAXBException {

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleOrder> strat = new HeaderColumnNameTranslateMappingStrategy<GsleOrder>();
      strat.setType(GsleOrder.class);
      Map<String, String> map = Maps.newHashMap();

      map.put("id", "idString");
      map.put("created_by", "createdByIdString");
      map.put("created_at", "createdDateString");
      map.put("modified_by", "modifiedByIdString");
      map.put("modified_at", "modifiedDateString");
      map.put("status", "status");
      map.put("project", "project");
      map.put("platform", "platform");

      strat.setColumnMapping(map);

      CsvToBean<GsleOrder> csvToBean = new CsvToBean<GsleOrder>();
      List<GsleOrder> gsleOrder = csvToBean.parse(strat, csvReader);

      List<Order> orders = Lists.newArrayList();
      for (Order defaultOrder : gsleOrder) {
         orders.add(defaultOrder);
      }

      // List<Temporary> getTemporaryList = getTemporary();
      // Map<Integer, Set<Attribute>> attributeOrderMap =
      // attributeOrderMap(getTemporaryList);
      // Map<Integer, Set<OrderSample>> sampleOrderMap =
      // sampleOrderMap(getTemporaryList);
      //
      // java.util.ListIterator<Order> it = orders.listIterator();
      // List<Order> finalOrder = Lists.newArrayList();

      // while (it.hasNext()) {
      // Order order = (Order) it.next();
      // if (sampleOrderMap.containsKey((order.getId()))) {
      // System.out.println(order.getId());
      // Set<OrderSample> samples = sampleOrderMap.get(order.getId());
      // for (OrderSample orderSample : samples) {
      // Set<Attribute> attributes = attributeOrderMap.get(order.getId());
      // orderSample.setAttributes(attributes);
      // temporaryOrderSampleSet.add(orderSample);
      // for (OrderSample newOrderSample : temporaryOrderSampleSet) {
      // for (Order orderList : orders) {
      // Set<OrderSample> orderSampleSet = Sets.newHashSet(newOrderSample);
      // orderList.setSample(orderSampleSet);
      // finalOrder.add(orderList);
      // orderSampleSet.clear();
      // }
      // }
      // }
      // }
      // }

      // ///////////////////////////////////////////////////////////////////////////////
      // ///////////////////////////////////////////////////////////////////////////////
      // ///////////////////////////////////////////////////////////////////////////////
      // ////////////////////////////////////////////////////////////////////

      // for (Order order : orders) {
      // if (sampleOrderMap.containsKey((order.getId()))) {
      // Set<OrderSample> samples = sampleOrderMap.get(order.getId());
      // for (OrderSample orderSample : samples) {
      // Set<OrderSample> temporaryOrderSampleSet = Sets.newHashSet();
      // order.setSample(temporaryOrderSampleSet);
      // Set<Attribute> attributes = attributeOrderMap.get(order.getId());
      // orderSample.setAttributes(attributes);
      // temporaryOrderSampleSet.add(orderSample);
      // finalOrder.add(order);
      // temporaryOrderSampleSet.clear();
      // System.out.println(finalOrder);
      // System.out.println();
      // System.out.println();
      // System.out.println();
      // }
      // }
      // }

      List<Temporary> getTemporaryList = getTemporary();

      Map<Integer, Set<Attribute>> attributeOrderMap = attributeOrderMap(getTemporaryList);
      Map<Integer, Set<OrderSample>> sampleOrderMap = sampleOrderMap(getTemporaryList);
      Set<OrderSample> temporaryOrderSampleSet = Sets.newHashSet();
      // java.util.ListIterator<Order> it = orders.listIterator();
      List<Order> finalOrder = Lists.newArrayList();

      for (Order order : orders) {
         if (sampleOrderMap.containsKey(order.getId())) {
            Set<OrderSample> samples = sampleOrderMap.get(order.getId());
            for (OrderSample orderSample : samples) {
               Set<Attribute> attributes = attributeOrderMap.get(order.getId());
               temporaryOrderSampleSet = Sets.newHashSet();
               orderSample.setAttributes(attributes);
               temporaryOrderSampleSet.add(orderSample);
               order.setSample(temporaryOrderSampleSet);
               finalOrder.add(order);
            }
         }
      }

      return finalOrder;
   }

   public List<Temporary> createMap(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<Temporary> strat = new HeaderColumnNameTranslateMappingStrategy<Temporary>();
      strat.setType(Temporary.class);
      Map<String, String> map = Maps.newHashMap();

      map.put("sample_id", "idSampleString");
      map.put("order_id", "idOrderString");
      map.put("barcode", "barcode");
      map.put("name", "name");
      map.put("value", "value");

      strat.setColumnMapping(map);

      CsvToBean<Temporary> csvToBean = new CsvToBean<Temporary>();
      List<Temporary> gsleTemp = csvToBean.parse(strat, csvReader);

      List<Temporary> temp = Lists.newArrayList();
      for (Temporary temporary : gsleTemp) {
         temp.add(temporary);
      }

      return temp;

   }

   public Map<Integer, Set<Attribute>> attributeOrderMap(List<Temporary> temp) {

      Map<Integer, Set<Attribute>> attMap = Maps.newHashMap();

      for (Temporary list : temp) {

         if (attMap.containsKey(list.getOrderId())) {
            Attribute attribute = new DefaultAttribute();
            attribute.setId(list.getOrderId());
            attribute.setName(list.getName());
            attribute.setValue(list.getValue());

            attMap.get(list.getOrderId()).add(attribute);

         } else {
            Attribute attribute = new DefaultAttribute();
            Set<Attribute> attributeSet = Sets.newHashSet();

            attribute.setId(list.getOrderId());
            attribute.setName(list.getName());
            attribute.setValue(list.getValue());
            attributeSet.add(attribute);

            attMap.put(list.getOrderId(), attributeSet);

         }
      }

      return attMap;

   }

   public Map<Integer, Set<OrderSample>> sampleOrderMap(List<Temporary> temp) {

      Map<Integer, Set<OrderSample>> attMap = Maps.newHashMap();

      for (Temporary list : temp) {

         if (attMap.containsKey(list.getOrderId())) {

            OrderSample orderSample = new DefaultOrderSample();
            orderSample.setBarcode(list.getBarcode());
            orderSample.setId(list.getSampleId());

            attMap.get(list.getOrderId()).add(orderSample);

         } else {

            OrderSample orderSample = new DefaultOrderSample();
            Set<OrderSample> orderSampleSet = Sets.newHashSet();

            orderSample.setBarcode(list.getBarcode());
            orderSample.setId(list.getSampleId());
            orderSampleSet.add(orderSample);
            attMap.put(list.getOrderId(), orderSampleSet);

         }
      }

      return attMap;
   }

   public List<Temporary> getTemporary() {
      List<Temporary> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl("184520");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = createMap(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }

      return result;
   }

   public Temporary getTemporary(Integer id) {
      Temporary result = null;

      StringBuilder url = getBaseUrl("184521");
      url.append(";bind=");
      url.append(id);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<Temporary> temporary = createMap(br);
         if (temporary.size() == 1) {
            result = temporary.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   /*
    * ///////////////////////////////////////////////////////////////////////////
    * MERGE
    * 
    * //////////////////////////////////////////////////////////////////////////
    */

   @Override
   public List<Order> getOrders() {
      getTemporary();

      List<Order> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl("182404");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getOrders(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public Order getOrder(Integer id) {
      // /////////////////////////////////////////////////////////////////////////////////////////////
      // ////////////////////////////////////////////////////////////////////////////////////////////
      // getTemporary(id);
      // /////////////////////////////////////////////////////////////////////////////////////////////
      // ////////////////////////////////////////////////////////////////////////////////////////////
      Order result = null;

      StringBuilder url = getBaseUrl("182405");
      url.append(";bind=");
      url.append(id);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<Order> orders = getOrders(br);
         if (orders.size() == 1) {
            result = orders.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   List<User> getUsers(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleUser> strat = new HeaderColumnNameTranslateMappingStrategy<GsleUser>();
      strat.setType(GsleUser.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("user_id", "idString");
      map.put("title", "title");
      map.put("firstname", "firstname");
      map.put("lastname", "lastname");
      map.put("institution", "institution");
      map.put("phone", "phone");
      map.put("email", "email");
      map.put("active", "archivedString");
      map.put("lab_comment", "comment");
      map.put("created_at", "createdString");
      map.put("created_by", "createdByIdString");
      map.put("modified_at", "modifiedString");
      map.put("modified_by", "modifiedByIdString");
      strat.setColumnMapping(map);

      CsvToBean<GsleUser> csvToBean = new CsvToBean<GsleUser>();
      List<GsleUser> defaultUsers = csvToBean.parse(strat, csvReader);
      List<User> samples = Lists.newArrayList();
      for (User defaultUser : defaultUsers) {
         samples.add(defaultUser);
      }

      return samples;
   }

   @Override
   public List<ChangeLog> getChangeLogs() {
      List<ChangeLog> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl("76371");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getChangeLogs(br);
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   List<ChangeLog> getChangeLogs(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleChange> strat = new HeaderColumnNameTranslateMappingStrategy<GsleChange>();
      strat.setType(GsleChange.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("template_id", "sampleIdString");
      map.put("cmnt", "comment");
      map.put("notes", "action");
      map.put("created_by", "createdByIdString");
      map.put("created_at", "createdString");
      strat.setColumnMapping(map);

      CsvToBean<GsleChange> csvToBean = new CsvToBean<GsleChange>();
      List<GsleChange> defaultUsers = csvToBean.parse(strat, csvReader);
      List<Change> changes = Lists.newArrayList();
      for (Change defaultUser : defaultUsers) {
         changes.add(defaultUser);
      }

      return getChangeLogs(changes);
   }

   List<ChangeLog> getChangeLogs(List<Change> changes) {
      Map<Integer, ChangeLog> changeLogMap = Maps.newHashMap();

      for (Change change : changes) {
         if (changeLogMap.containsKey(((GsleChange) change).getSampleId())) {
            changeLogMap.get(((GsleChange) change).getSampleId()).getChanges().add(change);
         } else {
            ChangeLog changeLog = new DefaultChangeLog();
            changeLog.setSampleId(((GsleChange) change).getSampleId());
            Set<Change> changeSet = Sets.newHashSet();
            changeSet.add(change);
            changeLog.setChanges(changeSet);
            changeLogMap.put(((GsleChange) change).getSampleId(), changeLog);
         }
      }
      return new ArrayList<ChangeLog>(changeLogMap.values());
   }

   @Override
   public ChangeLog getChangeLog(Integer id) {
      ChangeLog result = null;

      StringBuilder url = getBaseUrl("76372");
      url.append(";bind=");
      url.append(id);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<ChangeLog> changeLogs = getChangeLogs(br);
         if (changeLogs.size() == 1) {
            result = changeLogs.get(0);
         }
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public List<InstrumentModel> getInstrumentModels() {
      List<InstrumentModel> result = Lists.newArrayList();

      // TODO: Create query and update mapping
      StringBuilder url = getBaseUrl("74418");
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getInstrumentModels(br);
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   List<InstrumentModel> getInstrumentModels(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleInstrumentModel> strat = new HeaderColumnNameTranslateMappingStrategy<GsleInstrumentModel>();
      strat.setType(GsleInstrumentModel.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("model_id", "idString");
      map.put("name", "name");
      // map.put("vendor", "vendor");
      map.put("created_at", "createdString");
      map.put("created_by", "createdByIdString");
      map.put("modified_at", "modifiedString");
      map.put("modified_by", "modifiedByIdString");
      strat.setColumnMapping(map);

      CsvToBean<GsleInstrumentModel> csvToBean = new CsvToBean<GsleInstrumentModel>();
      List<GsleInstrumentModel> defaultUsers = csvToBean.parse(strat, csvReader);
      List<InstrumentModel> samples = Lists.newArrayList();
      for (InstrumentModel defaultUser : defaultUsers) {
         samples.add(defaultUser);
      }

      return samples;
   }

   @Override
   public InstrumentModel getInstrumentModel(Integer id) {
      InstrumentModel result = null;

      StringBuilder url = getBaseUrl("117701");
      url.append(";bind=");
      url.append(id);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<InstrumentModel> instrumentModels = getInstrumentModels(br);
         if (instrumentModels.size() == 1) {
            result = instrumentModels.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public List<Instrument> getInstruments(Integer instrumentModelId) {
      List<Instrument> result = Lists.newArrayList();

      // TODO: Create query and update mapping
      StringBuilder url = getBaseUrl("117755");
      url.append(";bind=");
      url.append(instrumentModelId);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getInstruments(br);
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   List<Instrument> getInstruments(Reader reader) {
      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleInstrument> strat = new HeaderColumnNameTranslateMappingStrategy<GsleInstrument>();
      strat.setType(GsleInstrument.class);
      Map<String, String> map = Maps.newHashMap();
      map.put("instr_id", "idString");
      map.put("name", "name");
      map.put("created_at", "createdString");
      strat.setColumnMapping(map);

      CsvToBean<GsleInstrument> csvToBean = new CsvToBean<GsleInstrument>();
      List<GsleInstrument> defaultInstruments = csvToBean.parse(strat, csvReader);
      List<Instrument> samples = Lists.newArrayList();
      for (Instrument defaultUser : defaultInstruments) {
         samples.add(defaultUser);
      }

      return samples;
   }

   @Override
   public Instrument getInstrument(Integer instrumentModelId, Integer instrumentId) {
      Instrument result = null;

      StringBuilder url = getBaseUrl("118008");
      url.append(";bind=");
      url.append(instrumentModelId);
      url.append(";bind=");
      url.append(instrumentId);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         List<Instrument> instruments = getInstruments(br);
         if (instruments.size() == 1) {
            result = instruments.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @VisibleForTesting
   void setBarcodeMap(Map<String, String> map) {

      this.barcodeMap = map;

   }

   public Attribute barcodeFilter(Attribute attr) {

      if (attr.getName().equals("Barcode")) {
         String name = attr.getValue();

         if (barcodeMap.size() == 0) {
            getBarcode();
         }

         String barcode = (String) barcodeMap.get(name);

         if (barcode != null) {
            attr.setValue(barcode);
         }
      }
      return attr;
   }
}
