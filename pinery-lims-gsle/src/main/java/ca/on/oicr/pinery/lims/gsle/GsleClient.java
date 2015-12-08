package ca.on.oicr.pinery.lims.gsle;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultAttributeName;
import ca.on.oicr.pinery.lims.DefaultChangeLog;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultType;
import ca.on.oicr.pinery.lims.GsleAttribute;
import ca.on.oicr.pinery.lims.GsleChange;
import ca.on.oicr.pinery.lims.GsleInstrument;
import ca.on.oicr.pinery.lims.GsleInstrumentModel;
import ca.on.oicr.pinery.lims.GsleOrder;
import ca.on.oicr.pinery.lims.GsleRun;
import ca.on.oicr.pinery.lims.GsleSample;
import ca.on.oicr.pinery.lims.GsleSampleChildren;
import ca.on.oicr.pinery.lims.GsleSampleParents;
import ca.on.oicr.pinery.lims.GsleUser;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

public class GsleClient implements Lims {

   // Constructing new map
   private Map<String, String> barcodeMap = Maps.newHashMap();

   private static final Logger log = LoggerFactory.getLogger(GsleClient.class);

   public final static String UTF8 = "UTF8";

   private String key;
   private String url;
   private String attributes;
   private String barcode;
   private String children;
   private String parents;
   private String samples;
   private String usersList;
   private String userSingle;
   private String temporaryOrderList;
   private String temporaryOrderSingle;
   private String ordersList;
   private String orderSingle;
   private String temporaryRunsList;
   private String temporaryRunSingle;
   private String runsList;
   private String runSingle;
   private String runByName;
   private String changeLogsList;
   private String changeLogSingle;
   private String instrumentModelsList;
   private String instrumentModelSingle;
   private String instrumentsList;
   private String instrumentModelInstrumentList;
   private String instrumentSingle;
   private String sampleIdSingle;

   public void setSampleIdSingle(String sampleIdSingle) {
      this.sampleIdSingle = sampleIdSingle;
   }

   public void setInstrumentSingle(String instrumentSingle) {
      this.instrumentSingle = instrumentSingle;
   }

   public void setInstrumentModelInstrumentList(String instrumentModelInstrumentList) {
      this.instrumentModelInstrumentList = instrumentModelInstrumentList;
   }

   public void setInstrumentsList(String instrumentsList) {
      this.instrumentsList = instrumentsList;
   }

   public void setInstrumentModelSingle(String instrumentModelSingle) {
      this.instrumentModelSingle = instrumentModelSingle;
   }

   public void setInstrumentModelsList(String instrumentModelsList) {
      this.instrumentModelsList = instrumentModelsList;
   }

   public void setChangeLogSingle(String changeLogSingle) {
      this.changeLogSingle = changeLogSingle;
   }

   public void setChangeLogsList(String changeLogsList) {
      this.changeLogsList = changeLogsList;
   }

   public void setRunSingle(String runSingle) {
      this.runSingle = runSingle;
   }
   
   public void setRunByName(String runByName) {
     this.runByName = runByName;
   }

   public void setRunsList(String runsList) {
      this.runsList = runsList;
   }

   public void setTemporaryRunSingle(String temporaryRunSingle) {
      this.temporaryRunSingle = temporaryRunSingle;
   }

   public void setTemporaryRunsList(String temporaryRunsList) {
      this.temporaryRunsList = temporaryRunsList;
   }

   public void setOrderSingle(String orderSingle) {
      this.orderSingle = orderSingle;
   }

   public void setOrdersList(String ordersList) {
      this.ordersList = ordersList;
   }

   public void setTemporaryOrderSingle(String temporaryOrderSingle) {
      this.temporaryOrderSingle = temporaryOrderSingle;
   }

   public void setTemporaryOrderList(String temporaryOrderList) {
      this.temporaryOrderList = temporaryOrderList;
   }

   public void setUserSingle(String userSingle) {
      this.userSingle = userSingle;
   }

   public void setUsersList(String userList) {
      this.usersList = userList;
   }

   public void setSamples(String samples) {
      this.samples = samples;
   }

   public void setParents(String parents) {
      this.parents = parents;
   }

   public void setChildren(String children) {
      this.children = children;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public void setAttributes(String attributes) {
      this.attributes = attributes;
   }

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

      StringBuilder url = getBaseUrl(attributes);
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

      StringBuilder url = getBaseUrl(barcode);

      try {
         ClientRequest request = new ClientRequest(url.toString());
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));

         try (CSVReader reader = new CSVReader(new BufferedReader(br), '\t')) {
	         String[] nextLine;
	
	         while ((nextLine = reader.readNext()) != null) {
	            barcodeMap.put(nextLine[0], nextLine[1]);
	         }
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

      StringBuilder url = getBaseUrl(children);
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

      StringBuilder url = getBaseUrl(parents);
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

      StringBuilder sb = getBaseUrl(samples);
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

      Sample result = null;

      StringBuilder url = getBaseUrl(sampleIdSingle);
      url.append(";bind=");
      url.append(id);

      // log.error("Inside getSample with id [{}]", id);
      try {

         ClientRequest request = new ClientRequest(url.toString());

         request.accept("text/plain");
         // log.error("The uri is [{}].", request.getUri());
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));

         List<Sample> samples = getSamples(br);

         if (samples.size() == 1) {
            result = samples.get(0);

         }

      } catch (Exception e) {
         System.out.println(e);
      }

      return result;

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

      StringBuilder url = getBaseUrl(usersList);

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

      StringBuilder url = getBaseUrl(userSingle);
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

      List<TemporaryOrder> getTemporary = getTemporaryOrder();
      Map<String, Set<Attribute>> attributeOrderMap = attributeOrderMap(getTemporary);
      Map<Integer, Set<OrderSample>> sampleOrderMap = sampleOrderMap(getTemporary);

      for (Order order : orders) {
         if (sampleOrderMap.containsKey(order.getId())) {
            Set<OrderSample> samples = sampleOrderMap.get(order.getId());
            for (OrderSample orderSample : samples) {
               if (attributeOrderMap.containsKey(order.getId() + "_" + orderSample.getId())) {
                  Set<Attribute> attributes = attributeOrderMap.get(order.getId() + "_" + orderSample.getId());
                  orderSample.setAttributes(attributes);

               }
            }
            order.setSample(samples);
         }
      }

      return orders;
   }

   List<Order> getOrders(Reader reader, Integer id) throws SAXException, JAXBException {
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

      List<TemporaryOrder> getTemporary = getTemporaryOrder(id);
      Map<String, Set<Attribute>> attributeOrderMap = attributeOrderMap(getTemporary);
      Map<Integer, Set<OrderSample>> sampleOrderMap = sampleOrderMap(getTemporary);

      for (Order order : orders) {
         if (sampleOrderMap.containsKey(order.getId())) {
            Set<OrderSample> samples = sampleOrderMap.get(order.getId());
            for (OrderSample orderSample : samples) {
               if (attributeOrderMap.containsKey(order.getId() + "_" + orderSample.getId())) {
                  Set<Attribute> attributes = attributeOrderMap.get(order.getId() + "_" + orderSample.getId());
                  orderSample.setAttributes(attributes);
               }
            }
            order.setSample(samples);
         }
      }
      return orders;
   }

   public List<TemporaryOrder> createMap(Reader reader) throws IOException {

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<TemporaryOrder> strat = new HeaderColumnNameTranslateMappingStrategy<TemporaryOrder>();
      strat.setType(TemporaryOrder.class);
      Map<String, String> map = Maps.newHashMap();

      map.put("sample_id", "idSampleString");
      map.put("order_id", "idOrderString");
      map.put("barcode", "barcode");
      map.put("barcode_two", "barcodeTwo");
      map.put("name", "name");
      map.put("value", "value");
      map.put("sample_url", "sampleUrl");

      strat.setColumnMapping(map);

      CsvToBean<TemporaryOrder> csvToBean = new CsvToBean<TemporaryOrder>();
      List<TemporaryOrder> gsleTemp = csvToBean.parse(strat, csvReader);

      List<TemporaryOrder> temp = Lists.newArrayList();
      for (TemporaryOrder temporary : gsleTemp) {
         temp.add(temporary);
      }

      return temp;

   }

   public Map<String, Set<Attribute>> attributeOrderMap(List<TemporaryOrder> temp) {

      Map<String, Set<Attribute>> attMap = Maps.newHashMap();

      for (TemporaryOrder list : temp) {

         if (attMap.containsKey(list.getOrderId() + "_" + list.getSampleId())) {

            Attribute attribute = new DefaultAttribute();
            attribute.setId(list.getOrderId());
            attribute.setName(list.getName());
            attribute.setValue(list.getValue());

            attMap.get(list.getOrderId() + "_" + list.getSampleId()).add(attribute);

         } else {

            Attribute attribute = new DefaultAttribute();
            Set<Attribute> attributeSet = Sets.newHashSet();

            attribute.setId(list.getOrderId());
            attribute.setName(list.getName());
            attribute.setValue(list.getValue());
            attributeSet.add(attribute);

            attMap.put(list.getOrderId() + "_" + list.getSampleId(), attributeSet);
         }
      }

      return attMap;
   }

   public Map<Integer, Set<OrderSample>> sampleOrderMap(List<TemporaryOrder> temp) {
      Map<Integer, Set<OrderSample>> attMap = Maps.newHashMap();

      for (TemporaryOrder list : temp) {

         if (attMap.containsKey(list.getOrderId())) {

            OrderSample orderSample = new DefaultOrderSample();
            orderSample.setBarcode(list.getBarcode());
            orderSample.setBarcodeTwo(list.getBarcodeTwo());
            orderSample.setId(list.getSampleId());
            orderSample.setUrl(list.getSampleUrl());
            attMap.get(list.getOrderId()).add(orderSample);

         } else {

            OrderSample orderSample = new DefaultOrderSample();
            Set<OrderSample> orderSampleSet = Sets.newHashSet();

            orderSample.setBarcode(list.getBarcode());
            orderSample.setBarcodeTwo(list.getBarcodeTwo());
            orderSample.setId(list.getSampleId());
            orderSample.setUrl(list.getSampleUrl());
            orderSampleSet.add(orderSample);
            attMap.put(list.getOrderId(), orderSampleSet);
         }
      }
      return attMap;
   }

   public List<TemporaryOrder> getTemporaryOrder() {
      List<TemporaryOrder> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(temporaryOrderList);
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

   public List<TemporaryOrder> getTemporaryOrder(Integer id) {
      List<TemporaryOrder> result = null;

      StringBuilder url = getBaseUrl(temporaryOrderSingle);

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
         List<TemporaryOrder> temporary = createMap(br);
         result = temporary;

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public List<Order> getOrders() {

      List<Order> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(ordersList);
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

      Order result = null;

      StringBuilder url = getBaseUrl(orderSingle);
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
         List<Order> orders = getOrders(br, id);
         if (orders.size() == 1) {
            result = orders.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   List<Run> getRuns(Reader reader) throws SAXException, JAXBException {

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<GsleRun> strat = new HeaderColumnNameTranslateMappingStrategy<GsleRun>();
      strat.setType(GsleRun.class);
      Map<String, String> map = Maps.newHashMap();

      map.put("state", "state");
      map.put("name", "name");
      map.put("barcode", "barcode");
      map.put("created_by", "createdByIdString");
      map.put("created_at", "createdDateString");
      map.put("id", "idString");
      map.put("instr_id", "instrumentIdString");
      map.put("instrument_name", "instrumentName");

      strat.setColumnMapping(map);

      CsvToBean<GsleRun> csvToBean = new CsvToBean<GsleRun>();
      List<GsleRun> gsleRun = csvToBean.parse(strat, csvReader);

      List<Run> runs = Lists.newArrayList();
      for (Run defaultRun : gsleRun) {
         runs.add(defaultRun);
      }

      List<TemporaryRun> getTemporary = runs.size() == 1 ? getTemporaryRun(runs.get(0).getId()) : getTemporaryRun();
      Table<Integer, Integer, Set<RunSample>> table = positionMapGenerator(getTemporary);

      for (Run run : runs) {
         if (table.containsRow(run.getId())) {
            Map<Integer, Set<RunSample>> tableMap = table.row(run.getId());
            Set<RunPosition> runPositionSet = Sets.newHashSet();
            run.setSample(runPositionSet);
            for (Map.Entry<Integer, Set<RunSample>> entry : tableMap.entrySet()) {
               RunPosition runPosition = new DefaultRunPosition();
               runPosition.setPosition(entry.getKey());
               runPosition.setRunSample(entry.getValue());
               runPositionSet.add(runPosition);
            }
         }
      }

      return runs;
   }

   public List<TemporaryRun> createMapRun(Reader reader) throws IOException {

      CSVReader csvReader = new CSVReader(reader, '\t');
      HeaderColumnNameTranslateMappingStrategy<TemporaryRun> strat = new HeaderColumnNameTranslateMappingStrategy<TemporaryRun>();
      strat.setType(TemporaryRun.class);
      Map<String, String> map = Maps.newHashMap();

      map.put("sample_id", "idSampleString");
      map.put("run_id", "idRunString");
      map.put("position", "positionString");
      map.put("barcode", "barcode");
      map.put("barcode_two", "barcodeTwo");
      map.put("sample_url", "sampleUrl");

      strat.setColumnMapping(map);

      CsvToBean<TemporaryRun> csvToBean = new CsvToBean<TemporaryRun>();
      List<TemporaryRun> gsleTemp = csvToBean.parse(strat, csvReader);

      List<TemporaryRun> temp = Lists.newArrayList();
      for (TemporaryRun temporary : gsleTemp) {
         if (temporary.getPosition() != -999) {
            temp.add(temporary);
         }
      }

      return temp;

   }

   public Table<Integer, Integer, Set<RunSample>> positionMapGenerator(List<TemporaryRun> positions) {
      Table<Integer, Integer, Set<RunSample>> table = HashBasedTable.create();

      for (TemporaryRun temp : positions) {
         RunSample runSample = new DefaultRunSample();
         runSample.setBarcode(temp.getBarcode());
         runSample.setBarcodeTwo(temp.getBarcodeTwo());
         runSample.setId(temp.getSampleId());
         if (table.contains(temp.getRunId(), temp.getPosition())) {

            table.get(temp.getRunId(), temp.getPosition()).add(runSample);
         } else {
            Set<RunSample> runSampleSet = Sets.newHashSet();
            runSampleSet.add(runSample);
            table.put(temp.getRunId(), temp.getPosition(), runSampleSet);
         }
      }
      return table;
   }

   public Map<Integer, Set<RunSample>> positionRunMap(List<TemporaryRun> temp) {

      Map<Integer, Set<RunSample>> attMap = Maps.newHashMap();

      for (TemporaryRun list : temp) {

         if (attMap.containsKey(list.getRunId())) {
            RunSample runSample = new DefaultRunSample();
            runSample.setId(list.getSampleId());
            runSample.setBarcode(list.getBarcode());
            runSample.setBarcodeTwo(list.getBarcodeTwo());
            runSample.setUrl(list.getSampleUrl());

            attMap.get(list.getRunId()).add(runSample);

         } else {
            RunSample runSample = new DefaultRunSample();
            Set<RunSample> runSampleSet = Sets.newHashSet();

            runSample.setId(list.getSampleId());
            runSample.setBarcode(list.getBarcode());
            runSample.setBarcodeTwo(list.getBarcodeTwo());
            runSample.setUrl(list.getSampleUrl());
            runSampleSet.add(runSample);

            attMap.put(list.getRunId(), runSampleSet);
         }
      }

      return attMap;
   }

   public Map<Integer, Set<RunPosition>> samplePositionMap(List<TemporaryRun> temp) {
      Map<Integer, Set<RunPosition>> attMap = Maps.newHashMap();

      for (TemporaryRun list : temp) {

         if (attMap.containsKey(list.getRunId())) {

            RunPosition runPosition = new DefaultRunPosition();
            runPosition.setPosition(list.getPosition());
            attMap.get(list.getRunId()).add(runPosition);

         } else {

            RunPosition runPosition = new DefaultRunPosition();
            Set<RunPosition> runPositionSet = Sets.newHashSet();

            runPosition.setPosition(list.getPosition());
            runPositionSet.add(runPosition);
            attMap.put(list.getRunId(), runPositionSet);
         }
      }
      return attMap;
   }

   public List<TemporaryRun> getTemporaryRun() {
      List<TemporaryRun> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(temporaryRunsList);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = createMapRun(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }

      return result;
   }

   public List<TemporaryRun> getTemporaryRun(Integer id) {
      List<TemporaryRun> result = null;

      StringBuilder url = getBaseUrl(temporaryRunSingle);
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
         List<TemporaryRun> temporary = createMapRun(br);
         result = temporary;

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public List<Run> getRuns() {

      List<Run> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(runsList);
      try {
         ClientRequest request = new ClientRequest(url.toString());
         request.accept("text/plain");
         ClientResponse<String> response = request.get(String.class);

         if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
         result = getRuns(br);

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }

   @Override
   public Run getRun(Integer id) {
      Run result = null;

      StringBuilder url = getBaseUrl(runSingle);
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
         List<Run> runs = getRuns(br);
         if (runs.size() == 1) {
            result = runs.get(0);
         }

      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
      return result;
   }
   
   @Override
   public Run getRun(String runName) {
     Run result = null;
     
     StringBuilder url = getBaseUrl(runByName);
     url.append(";bind=");
     url.append(runName);
     
     try {
       ClientRequest request = new ClientRequest(url.toString());
       request.accept("text/plain");
       ClientResponse<String> response = request.get(String.class);

       if (response.getStatus() != 200) {
          throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
       }

       BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes(UTF8)), UTF8));
       List<Run> runs = getRuns(br);
       if (runs.size() == 1) {
          result = runs.get(0);
       }

    } catch (Exception e) {
       System.out.println(e);
       e.printStackTrace(System.out);
    }
    return result;
   }

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

      StringBuilder url = getBaseUrl(changeLogsList);
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

      StringBuilder url = getBaseUrl(changeLogSingle);
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

      StringBuilder url = getBaseUrl(instrumentModelsList);

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

      StringBuilder url = getBaseUrl(instrumentModelSingle);

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
   public List<Instrument> getInstruments(/* Integer instrumentModelId */) {
      List<Instrument> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(instrumentsList);
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

   @Override
   public List<Instrument> getInstrumentModelInsrument(Integer instrumentModelId) {
      List<Instrument> result = Lists.newArrayList();

      StringBuilder url = getBaseUrl(instrumentModelInstrumentList);
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
      map.put("date_added", "createdString");
      map.put("model_id", "modelId");
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
   public Instrument getInstrument(Integer instrumentId) {
      Instrument result = null;

      StringBuilder url = getBaseUrl(instrumentSingle);

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

      if (attr.getName().equals("Barcode") || attr.getName().equals("Barcode Two")) {
         String name = attr.getValue();

         if (barcodeMap.size() == 0) {
            getBarcode();
         }

         String barcode = barcodeMap.get(name);

         if (barcode != null) {
            attr.setValue(barcode);
         }
      }
      return attr;
   }
}
