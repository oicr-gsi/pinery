package ca.on.oicr.pinery.lims.miso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultChangeLog;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultPreparationKit;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultType;
import ca.on.oicr.pinery.lims.DefaultUser;

public class MisoClient implements Lims {
  
  // InstrumentModel queries
  private static final String queryAllModels = "SELECT p.platformId, p.instrumentModel " +
      "FROM Platform as p";
  private static final String queryModelById = queryAllModels + " WHERE p.platformId = ?";
  
  // Instrument queries
  private static final String queryAllInstruments = "SELECT sr.referenceId, sr.name, sr.platformId " + 
      "FROM SequencerReference AS sr";
  private static final String queryInstrumentById = queryAllInstruments + " WHERE sr.referenceId = ?";
  private static final String queryInstrumentsByModelId = queryAllInstruments + " WHERE sr.platformId = ?";
  
  // Order queries
  private static final String queryAllOrders = "SELECT stat.health runStatus, prj.name project, pool.platformType platform, " +
      "pool.poolId, prj.projectId, createLog.userId createdBy, createLog.changeTime creationDate, updateLog.userId modifiedBy, " +
      "updateLog.changeTime modified " +
      "FROM Pool AS pool " +
      "JOIN Pool_Elements ele ON ele.elementType='uk.ac.bbsrc.tgac.miso.core.data.impl.LibraryDilution' " + // scary
      "AND ele.pool_poolId = pool.poolId " +
      "JOIN LibraryDilution ld ON ld.dilutionId = ele.elementId " +
      "JOIN Library l ON l.libraryId = ld.library_libraryId " +
      "JOIN Sample s ON s.sampleId = l.sample_sampleId " +
      "JOIN Project prj ON prj.projectId = s.project_projectId " +
      "JOIN (" +
        "SELECT poolId, userId, changeTime FROM PoolChangeLog WHERE changeTime IN (" +
          "SELECT MIN(changeTime) AS changeTime FROM PoolChangeLog GROUP BY poolId" +
        ")" +
      ") createLog ON createLog.poolId = pool.poolId " +
      "JOIN (" +
        "SELECT poolId, userId, changeTime FROM PoolChangeLog WHERE changeTime IN (" +
          "SELECT MAX(changeTime) AS changeTime FROM PoolChangeLog GROUP BY poolId" +
        ")" +
      ") updateLog ON createLog.poolId = pool.poolId " +
      "LEFT JOIN _Partition part ON part.pool_poolId = pool.poolId " +
      "LEFT JOIN SequencerPartitionContainer_Partition spcp ON spcp.partitions_partitionId = part.partitionId " +
      "LEFT JOIN SequencerPartitionContainer spc ON spc.containerId = spcp.container_containerId " +
      "LEFT JOIN Run_SequencerPartitionContainer rcpc ON rcpc.containers_containerId = spc.containerId " +
      "LEFT JOIN Run r ON r.runId = rcpc.Run_runId " +
      "LEFT JOIN Status stat ON stat.statusId = r.status_statusId " +
      "GROUP BY poolId, projectId";
  
  private static final String queryOrderById = queryAllOrders + " WHERE pool.poolId = ?";
  
  // User queries
  private static final String queryAllUsers = "SELECT u.userId, u.fullname, u.email, u.active " +
      "FROM User AS u";
  private static final String queryUserById = queryAllUsers + " WHERE u.userId = ?";
  
  // Run queries
  private static final String queryAllRuns = "SELECT r.alias, r.sequencerReference_sequencerReferenceId AS instrumentId, r.runId, " +
      "st.health, spc.identificationBarcode, createLog.userId, createLog.changeTime " +
      "FROM Run AS r " +
      "JOIN Status AS st ON st.statusId = r.status_statusId " +
      "JOIN Run_SequencerPartitionContainer AS rscp ON rscp.Run_runId = r.runId " +
      "JOIN SequencerPartitionContainer AS spc ON spc.containerId = rscp.containers_containerId " +
      "JOIN (" +
        "SELECT runId, userId, changeTime FROM RunChangeLog WHERE changeTime IN (" +
          "SELECT MIN(changeTime) AS changeTime FROM RunChangeLog GROUP BY runId" +
        ")" +
      ") createLog ON createLog.runId = r.runId";
  private static final String queryRunById = queryAllRuns + " WHERE r.runId = ?";
  private static final String queryRunByName = queryAllRuns + " WHERE r.alias = ?";
  
  // RunPosition queries
  private static final String queryAllRunPositions = "SELECT p.partitionId, p.partitionNumber, r_spc.Run_runId " +
      "FROM _Partition AS p " +
      "JOIN SequencerPartitionContainer_Partition AS spc_p ON spc_p.partitions_partitionId = p.partitionId " +
      "JOIN Run_SequencerPartitionContainer AS r_spc ON r_spc.containers_containerId = spc_p.container_containerId";
  private static final String queryRunPositionsByRunId = queryAllRunPositions + " WHERE r_spc.Run_runId = ?";
  
  // RunSample queries
  private static final String queryAllRunSamples = "SELECT part.partitionId, l.libraryId " +
      "FROM _Partition part " +
      "JOIN Pool pool ON pool.poolId = part.pool_poolId " +
      "JOIN Pool_Elements ele ON ele.elementType='uk.ac.bbsrc.tgac.miso.core.data.impl.LibraryDilution'" + // scary
      "AND ele.pool_poolId = pool.poolId " +
      "JOIN LibraryDilution ld ON ld.dilutionId = ele.elementId " +
      "JOIN Library l ON l.libraryId = ld.library_libraryId";
  private static final String queryRunSamplesByRunId = queryAllRunSamples + 
      " JOIN SequencerPartitionContainer_Partition spcp ON spcp.partitions_partitionId = part.partitionId" +
      " JOIN SequencerPartitionContainer spc ON spc.containerId = spcp.container_containerId" +
      " JOIN Run_SequencerPartitionContainer rcpc ON rcpc.containers_containerId = spc.containerId" +
      " WHERE rcpc.Run_runId = ?";
  
  // Sample queries
  private static final String queryAllSamples = "SELECT s.alias name, s.description description, s.sampleId id, " +
      "s.parentId parentId, sc.alias sampleType, tt.alias tissueType, p.alias project, sai.archived archived, " +
      "sai.creationDate created, sai.createdBy createdById, sai.lastUpdated modified, sai.updatedBy modifiedById, " +
      "s.identificationBarcode tubeBarcode, s.volume volume, sai.concentration concentration, s.locationBarcode " +
      "storageLocation, kd.name kitName, kd.description kitDescription " +
      "FROM Sample s " +
      "LEFT JOIN SampleAdditionalInfo sai ON sai.sampleId = s.sampleId " +
      "LEFT JOIN SampleClass sc ON sc.sampleClassId = sai.sampleClassId " +
      "LEFT JOIN TissueType tt ON tt.tissueTypeId = sai.tissueTypeId " +
      "LEFT JOIN Project p ON p.projectId = s.project_projectId " +
      "LEFT JOIN KitDescriptor kd ON kd.kitDescriptorId = sai.kitDescriptorId";
  private static final String queryAllSamplesFiltered = queryAllSamples +
      " WHERE sai.archived IN (?,?)" +
      " AND p.alias REGEXP ?" +
      " AND sc.alias REGEXP ?" +
      " AND sai.creationDate < ?" +
      " AND sai.lastUpdated > ?";
  private static final String querySampleById = queryAllSamples + " WHERE s.sampleId = ?";
  // TODO: Pinery "samples" also includes MISO Libraries
  
  // SampleType (MISO SampleClass and Library) queries
  private static final String queryAllSampleTypes = "SELECT sc.alias name, COUNT(*) count, " +
      "COUNT(CASE WHEN sai.archived = true THEN sai.archived END) archivedCount, MIN(sai.creationDate) earliest, " +
      "MAX(sai.lastUpdated) latest " +
      "FROM SampleAdditionalInfo sai " +
      "JOIN SampleClass sc ON sc.sampleClassId = sai.sampleClassId " +
      "GROUP BY sai.sampleClassId";
  // TODO: Include LibraryClasses too
  
  // SampleProject queries
  private static final String queryAllSampleProjects = "SELECT p.alias name, COUNT(*) count, " +
      "COUNT(CASE WHEN sai.archived = true THEN sai.archived END) archivedCount, MIN(sai.creationDate) earliest, " +
      "MAX(sai.lastUpdated) latest " +
      "FROM SampleAdditionalInfo sai " +
      "JOIN Sample s ON s.sampleId = sai.sampleId " +
      "JOIN Project p ON p.projectId = s.project_projectId " +
      "GROUP BY p.projectId";
  
  // SampleChangeLog queries
  private static final String queryAllSampleChangeLogs = "SELECT sampleId, message action, userId, changeTime " +
      "FROM SampleChangeLog";
  // TODO: Include LibraryChangeLogs too
  
  private static final String querySampleChangeLogById = queryAllSampleChangeLogs +
      " WHERE sampleId = ?";
  
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelRowMapper();
  private final RowMapper<Order> orderMapper = new OrderRowMapper();
  private final RowMapper<User> userMapper = new UserRowMapper();
  private final RowMapper<Run> runMapper = new RunRowMapper();
  private final RowMapper<MisoRunPosition> runPositionMapper = new RunPositionRowMapper();
  private final RowMapper<Sample> sampleMapper = new SampleRowMapper();
  private final RowMapper<MisoRunSample> runSampleMapper = new RunSampleRowMapper();
  private final RowMapper<Type> typeMapper = new TypeRowMapper();
  private final RowMapper<SampleProject> sampleProjectMapper = new SampleProjectMapper();
  private final RowMapper<MisoChange> changeMapper = new ChangeMapper();
  
  public static String makeInClause(String arg1, String... more) {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    if (arg1 != null) {
      appendInArg(sb, arg1);
    }
    for (String arg : more) {
      sb.append(",");
      appendInArg(sb, arg);
    }
    sb.append(")");
    return sb.toString();
  }
  
  private static void appendInArg(StringBuilder sb, String arg) {
    sb.append("'")
        .append(arg)
        .append("'");
  }
  
  private JdbcTemplate template;

  public MisoClient(JdbcTemplate template) {
    this.template = template;
  }
  
  public MisoClient(DataSource dataSource) {
    this.template = new JdbcTemplate(dataSource);
  }

  public JdbcTemplate getJdbcTemplate() {
    return template;
  }

  public void setJdbcTemplate(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public List<String> getProjects() {
    // This isn't connected to any endpoint (Not implemented in GSLE)
    return null;
  }

  @Override
  public Sample getSample(Integer id) {
    List<Sample> samples = template.query(querySampleById, new Object[]{id}, sampleMapper);
    return samples.size() == 1 ? samples.get(0) : null;
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    return template.query(queryAllSampleProjects, sampleProjectMapper);
  }

  @Override
  public List<Sample> getSamples(Boolean archived, Set<String> projects,
      Set<String> types, DateTime before, DateTime after) {
    Boolean archivedArg1 = archived == null ? false : archived;
    Boolean archivedArg2 = archived == null ? true : archived;
    String projectArg = projects == null || projects.isEmpty() ? ".*" : pipeDelimitSet(projects);
    String typeArg = types == null || types.isEmpty() ? ".*" : pipeDelimitSet(types);
    if (before == null) {
      before = DateTime.now().plusDays(1);
    }
    if (after == null) {
      after = DateTime.now().withYear(2005);
    }
    return template.query(queryAllSamplesFiltered, 
        new Object[]{archivedArg1, archivedArg2, projectArg, typeArg, before.toString(), after.toString()}, 
        sampleMapper);
  }
  
  private String pipeDelimitSet(Set<String> set) {
    StringBuilder sb = new StringBuilder();
    for (String item : set) {
      sb.append(item).append("|");
    }
    sb.deleteCharAt(sb.length()-1);
    return sb.toString();
  }
  
  public List<Sample> getSamples() {
    return getSamples(null, null, null, null, null);
  }

  @Override
  public List<User> getUsers() {
    return template.query(queryAllUsers, userMapper);
  }

  @Override
  public User getUser(Integer id) {
    List<User> users = template.query(queryUserById, new Object[]{id}, userMapper);
    return users.size() == 1 ? users.get(0) : null;
  }

  @Override
  public List<Order> getOrders() {
    List<Order> orders = template.query(queryAllOrders, orderMapper);
    List<MisoOrderSample> samples = getOrderSamples();
    Map<Integer, Order> map = new HashMap<>();
    for (Order o : orders) {
      map.put(o.getId(), o);
    }
    for (MisoOrderSample s : samples) {
      Order o = map.get(s.getOrderId());
      if (o != null) {
        Set<OrderSample> os = o.getSamples();
        if (os == null) {
          os = new HashSet<OrderSample>();
          o.setSample(os);
        }
        os.add(s);
      }
    }
    return orders;
  }

  @Override
  public Order getOrder(Integer id) {
    List<Order> orders = template.query(queryOrderById, new Object[]{id}, orderMapper);
    if (orders.size() != 1) return null;
    Order order = orders.get(0);
    Set<OrderSample> os = new HashSet<>();
    os.addAll(getOrderSamples(id));
    order.setSample(os);
    return order;
  }
  
  private List<MisoOrderSample> getOrderSamples() {
    // TODO: get all samples that are linked to any order
    return new ArrayList<MisoOrderSample>();
  }
  
  private List<MisoOrderSample> getOrderSamples(Integer orderId) {
    // TODO: get all samples with this order id (poolId)
    return new ArrayList<MisoOrderSample>();
  }
  
  private List<Order> mapSamplesToOrders(List<Order> orders, List<MisoOrderSample> samples) {
    Map<Integer, Order> map = new HashMap<>();
    for (Order o : orders) {
      map.put(o.getId(), o);
    }
    for (MisoOrderSample s : samples) {
      Order o = map.get(s.getOrderId());
      if (o != null) {
        Set<OrderSample> rs = o.getSamples();
        if (rs == null) {
          rs = new HashSet<OrderSample>();
          o.setSample(rs);
        }
        rs.add(s);
      }
    }
    return orders;
  }

  @Override
  public List<Run> getRuns() {
    List<Run> runs = template.query(queryAllRuns, runMapper);
    List<MisoRunPosition> positions = getRunPositions();
    Map<Integer, Run> map = new HashMap<>();
    for (Run r : runs) {
      map.put(r.getId(), r);
    }
    for (MisoRunPosition p : positions) {
      Run r = map.get(p.getRunId());
      if (r != null) {
        Set<RunPosition> rp = r.getSamples();
        if (rp == null) {
          rp = new HashSet<RunPosition>();
          r.setSample(rp);
        }
        rp.add(p);
      }
    }
    return runs;
  }

  @Override
  public Run getRun(Integer id) {
    return getSingleRun(queryRunById, new Object[]{id});
  }

  @Override
  public Run getRun(String runName) {
    return getSingleRun(queryRunByName, new Object[]{runName});
  }
  
  private Run getSingleRun(String query, Object[] params) {
    List<Run> runs = template.query(query, params, runMapper);
    if (runs.size() != 1) return null;
    Run run = runs.get(0);
    Set<RunPosition> rp = new HashSet<>();
    rp.addAll(getRunPositions(run.getId()));
    run.setSample(rp);
    return run;
  }
  
  private List<MisoRunPosition> getRunPositions() {
    List<MisoRunPosition> positions = template.query(queryAllRunPositions, runPositionMapper);
    List<MisoRunSample> samples = getRunSamples();
    return mapSamplesToPositions(positions, samples);
  }
  
  private List<MisoRunPosition> getRunPositions(Integer runId) {
    List<MisoRunPosition> positions = template.query(queryRunPositionsByRunId, new Object[]{runId},runPositionMapper);
    List<MisoRunSample> samples = getRunSamples(runId);
    return mapSamplesToPositions(positions, samples);
  }
  
  private List<MisoRunPosition> mapSamplesToPositions(List<MisoRunPosition> positions, List<MisoRunSample> samples) {
    Map<Integer, MisoRunPosition> map = new HashMap<>();
    for (MisoRunPosition p : positions) {
      map.put(p.getPartitionId(), p);
    }
    for (MisoRunSample s : samples) {
      MisoRunPosition p = map.get(s.getPartitionId());
      if (p != null) {
        Set<RunSample> rs = p.getRunSample();
        if (rs == null) {
          rs = new HashSet<RunSample>();
          p.setRunSample(rs);
        }
        rs.add(s);
      }
    }
    return positions;
  }
  
  private List<MisoRunSample> getRunSamples() {
    return template.query(queryAllRunSamples, runSampleMapper);
  }
  
  private List<MisoRunSample> getRunSamples(Integer runId) {
    return template.query(queryRunSamplesByRunId, new Object[]{runId}, runSampleMapper);
  }

  @Override
  public List<Type> getTypes() {
    return template.query(queryAllSampleTypes, typeMapper);
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    return mapChangesToChangeLogs(template.query(queryAllSampleChangeLogs, changeMapper));
  }

  @Override
  public ChangeLog getChangeLog(Integer id) {
    List<ChangeLog> changes = mapChangesToChangeLogs(template.query(querySampleChangeLogById, new Object[]{id}, changeMapper));
    return changes.size() == 1 ? changes.get(0) : null;
  }
  
  private List<ChangeLog> mapChangesToChangeLogs(List<MisoChange> changes) {
    Map<Integer, ChangeLog> map = new HashMap<>();
    for (MisoChange c : changes) {
     ChangeLog l = map.get(c.getSampleId());
     if (l == null) {
       l = new DefaultChangeLog();
       l.setSampleId(c.getSampleId());
       map.put(l.getSampleId(), l);
     }
     Set<Change> ch = l.getChanges();
     if (ch == null) {
       ch = new HashSet<>();
       l.setChanges(ch);
     }
     ch.add(c);
    }
    return new ArrayList<ChangeLog>(map.values());
  }

  @Override
  public List<InstrumentModel> getInstrumentModels() {
    return template.query(queryAllModels, modelMapper);
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    List<InstrumentModel> models = template.query(queryModelById, new Object[]{id}, modelMapper);
    return models.size() == 1 ? models.get(0) : null;
  }

  @Override
  public List<Instrument> getInstruments() {
    return template.query(queryAllInstruments, instrumentMapper);
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    List<Instrument> instruments = template.query(queryInstrumentById, new Object[]{instrumentId}, instrumentMapper);
    return instruments.size() == 1 ? instruments.get(0) : null;
  }

  @Override
  public List<Instrument> getInstrumentModelInsrument(Integer id) {
    return template.query(queryInstrumentsByModelId, new Object[]{id}, instrumentMapper);
  }
  
  private static class InstrumentMapper implements RowMapper<Instrument> {

    @Override
    public Instrument mapRow(ResultSet rs, int rowNum) throws SQLException {
      Instrument ins = new DefaultInstrument();
      
      ins.setId(rs.getInt("referenceId"));
      ins.setName(rs.getString("name"));
      ins.setInstrumentModel(rs.getString("platformId"));
      // TODO: createdDate
      
      return ins;
    }
    
  }
  
  private static class InstrumentModelRowMapper implements RowMapper<InstrumentModel> {

    @Override
    public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      InstrumentModel m = new DefaultInstrumentModel();
      
      m.setId(rs.getInt("platformId"));
      m.setName(rs.getString("instrumentModel"));
      // TODO: created, createdById, modified, modifiedById
      
      return m;
    }
    
  }
  
  private static class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
      Order o = new DefaultOrder();
      
      o.setId(rs.getInt("poolId")); // TODO: this needs to be String and include poolName+projectName (e.g. IPO1PRO1)
      o.setStatus(rs.getString("runStatus"));
      o.setProject(rs.getString("project"));
      o.setPlatform(rs.getString("platform"));
      o.setCreatedById(rs.getInt("createdBy"));
      o.setCreatedDate(rs.getTimestamp("creationDate"));
      o.setModifiedById(rs.getInt("modifiedBy"));
      o.setModifiedDate(rs.getTimestamp("modified"));
      
      return o;
    }
    
  }
  
  private static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User u = new DefaultUser();
      
      u.setId(rs.getInt("userId"));
      
      String fullname = rs.getString("fullname");
      if (fullname.contains(" ")) {
        u.setFirstname(fullname.substring(0, fullname.lastIndexOf(" ")));
        u.setLastname(fullname.substring(fullname.lastIndexOf(" ") + 1));
      }
      else {
        u.setFirstname(fullname);
        u.setLastname(fullname);
      }
      
      u.setEmail(rs.getString("email"));
      u.setArchived(!rs.getBoolean("active"));
      // TODO: title, institution, phone, comment, created, createdById, modified, modifiedById
      
      return u;
    }
    
  }
  
  private static class RunRowMapper implements RowMapper<Run> {

    @Override
    public Run mapRow(ResultSet rs, int rowNum) throws SQLException {
      Run r = new DefaultRun();
      
      r.setState(rs.getString("health"));
      r.setName(rs.getString("alias"));
      r.setBarcode(rs.getString("identificationBarcode"));
      r.setInstrumentId(rs.getInt("instrumentId"));
      r.setCreatedById(rs.getInt("createLog.userId"));
      r.setCreatedDate(rs.getTimestamp("createLog.changeTime"));
      r.setId(rs.getInt("runId"));
      return r;
    }
    
  }
  
  private static class RunPositionRowMapper implements RowMapper<MisoRunPosition> {

    @Override
    public MisoRunPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRunPosition p = new MisoRunPosition();
      
      p.setPosition(rs.getInt("partitionNumber"));
      p.setRunId(rs.getInt("Run_runId"));
      p.setPartitionId(rs.getInt("partitionId"));
      
      return p;
    }
    
  }
  
  private static class SampleRowMapper implements RowMapper<Sample> {

    @Override
    public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
      Sample s = new DefaultSample();
      
      s.setName(rs.getString("name"));
      s.setDescription(rs.getString("description"));
      s.setId(rs.getInt("id"));
      Integer parentId = rs.getInt("parentId");
      if (parentId != null) {
        Set<Integer> parents = new HashSet<>();
        parents.add(parentId);
        s.setParents(parents);
      };
      s.setSampleType(rs.getString("sampleType"));
      s.setTissueType(rs.getString("tissueType"));
      s.setProject(rs.getString("project"));
      s.setArchived(rs.getBoolean("archived"));
      s.setCreated(rs.getDate("created"));
      s.setCreatedById(rs.getInt("createdById"));
      s.setModified(rs.getDate("modified"));
      s.setModifiedById(rs.getInt("modifiedById"));
      s.setTubeBarcode(rs.getString("tubeBarcode"));
      s.setVolume(rs.getFloat("volume"));
      s.setConcentration(rs.getFloat("concentration"));
      s.setStorageLocation(rs.getString("storageLocation")); // TODO: base location on BoxPosition instead
      
      PreparationKit kit = new DefaultPreparationKit();
      kit.setName(rs.getString("kitName"));
      kit.setDescription(rs.getString("kitDescription"));
      if (kit.getName() != null || kit.getDescription() != null) {
        s.setPreparationKit(kit);
      }
      // TODO: children, attributes, status
      
      return s;
    }
    
  }
  
  private static class RunSampleRowMapper implements RowMapper<MisoRunSample> {

    @Override
    public MisoRunSample mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRunSample s = new MisoRunSample();
      
      s.setId(rs.getInt("l.libraryId"));
      s.setPartitionId(rs.getInt("part.partitionId"));
      
      return s;
    }
    
  }
  
  private static class TypeRowMapper implements RowMapper<Type> {

    @Override
    public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
      Type t = new DefaultType();
      
      t.setName(rs.getString("name"));
      t.setCount(rs.getInt("count"));
      t.setArchivedCount(rs.getInt("archivedCount"));
      t.setEarliest(rs.getTimestamp("earliest"));
      t.setLatest(rs.getTimestamp("latest"));
      
      return t;
    }
    
  }
  
  private static class SampleProjectMapper implements RowMapper<SampleProject> {

    @Override
    public SampleProject mapRow(ResultSet rs, int rowNum) throws SQLException {
      SampleProject p = new DefaultSampleProject();
      
      p.setName(rs.getString("name"));
      p.setCount(rs.getInt("count"));
      p.setArchivedCount(rs.getInt("archivedCount"));
      p.setEarliest(rs.getTimestamp("earliest"));
      p.setLatest(rs.getTimestamp("latest"));
      
      return p;
    }
    
  }
  
  private static class ChangeMapper implements RowMapper<MisoChange> {

    @Override
    public MisoChange mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoChange c = new MisoChange();
      
      c.setAction(rs.getString("action"));
      c.setCreated(rs.getTimestamp("changeTime"));
      c.setCreatedById(rs.getInt("userId"));
      c.setSampleId(rs.getInt("sampleId"));
      
      return c;
    }
    
  }
  
}
