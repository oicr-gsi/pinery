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
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultRun;
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
  private static final String queryAllOrders = "SELECT pool.ready, prj.name AS project, plat.name AS platform, pool.poolId " +
      "FROM Pool AS pool " +
      "JOIN Experiment AS ex ON ex.experimentId = pool.experiment_experimentId " +
      "JOIN Study AS st ON st.studyId = ex.study_studyId " +
      "JOIN Project AS prj ON prj.projectId = st.project_projectId " +
      "JOIN Platform AS plat ON plat.platformId = ex.platform_platformId";
  private static final String queryOrderById = queryAllOrders + " WHERE pool.poolId = ?";
  
  // User queries
  private static final String queryAllUsers = "SELECT u.userId, u.fullname, u.email, u.active " +
      "FROM User AS u";
  private static final String queryUserById = queryAllUsers + " WHERE u.userId = ?";
  
  // Run queries
  private static final String queryAllRuns = "SELECT r.alias, r.sequencerReference_sequencerReferenceId AS instrumentId, r.runId, " +
      "st.health, st.startDate, spc.identificationBarcode " +
      "FROM Run AS r " +
      "JOIN Status AS st ON st.statusId = r.status_statusId " +
      "JOIN Run_SequencerPartitionContainer AS rscp ON rscp.Run_runId = r.runId " +
      "JOIN SequencerPartitionContainer AS spc ON spc.containerId = rscp.containers_containerId";
  private static final String queryRunById = queryAllRuns + " WHERE r.runId = ?";
  private static final String queryRunByName = queryAllRuns + " WHERE r.alias = ?";
  
  // RunPosition queries
  private static final String queryAllRunPositions = "SELECT p.partitionNumber, p.pool_poolId, r_spc.Run_runId " +
      "FROM _Partition AS p " +
      "JOIN SequencerPartitionContainer_Partition AS spc_p ON spc_p.partitions_partitionId = p.partitionId " +
      "JOIN Run_SequencerPartitionContainer AS r_spc ON r_spc.containers_containerId = spc_p.container_containerId";
  private static final String queryRunPositionsByRunId = queryAllRunPositions + " WHERE r_spc.Run_runId = ?";
  
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelRowMapper();
  private final RowMapper<Order> orderMapper = new OrderRowMapper();
  private final RowMapper<User> userMapper = new UserRowMapper();
  private final RowMapper<Run> runMapper = new RunRowMapper();
  private final RowMapper<MisoRunPosition> runPositionMapper = new RunPositionRowMapper();
  
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Sample> getSamples(Boolean archived, Set<String> projects,
      Set<String> types, DateTime before, DateTime after) {
    // TODO Auto-generated method stub
    return null;
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
      map.put(p.getPoolId(), p);
    }
    for (MisoRunSample s : samples) {
      MisoRunPosition p = map.get(s.getPositionId());
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
    //TODO
    return new ArrayList<MisoRunSample>();
  }
  
  private List<MisoRunSample> getRunSamples(Integer runId) {
    //TODO
    return new ArrayList<MisoRunSample>();
  }

  @Override
  public List<Type> getTypes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ChangeLog getChangeLog(Integer id) {
    // TODO Auto-generated method stub
    return null;
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
      
      o.setId(rs.getInt("poolId"));
      o.setStatus(rs.getString("ready")); // TODO: I think this is wrong table field
      o.setProject(rs.getString("project"));
      o.setPlatform(rs.getString("platform"));
      // TODO: createdBy, createdDate, modifiedBy, modifiedDate
      
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
      r.setCreatedDate(rs.getTimestamp("startDate"));
      r.setId(rs.getInt("runId"));
      // TODO: createdById
      
      return r;
    }
    
  }
  
  private static class RunPositionRowMapper implements RowMapper<MisoRunPosition> {

    @Override
    public MisoRunPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRunPosition p = new MisoRunPosition();
      
      p.setPosition(rs.getInt("partitionNumber"));
      p.setRunId(rs.getInt("Run_runId"));
      p.setPoolId(rs.getInt("pool_poolId"));
      
      return p;
    }
    
  }
  
}
