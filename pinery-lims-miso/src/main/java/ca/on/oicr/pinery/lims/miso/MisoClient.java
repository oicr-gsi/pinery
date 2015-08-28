package ca.on.oicr.pinery.lims.miso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultUser;
import ca.on.oicr.pinery.lims.miso.util.ChildMapper;

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
  private static final String queryAllRunPositions = "SELECT p.partitionId, p.partitionNumber, p.poolId, r_spc.Run_runId " +
      "FROM _Partition AS p " +
      "JOIN SequencerPartitionContainer_Partition AS spc_p ON spc_p.partitions_partitionId = p.partitionId " +
      "JOIN Run_SequencerPartitionContainer AS r_spc ON r_spc.containers_containerId = spc_p.container_containerId";
  private static final String queryRunPositionsByRunId = queryAllRunPositions + " WHERE r_spc.Run_runId = ?";
  
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelRowMapper();
  private final RowMapper<MisoOrder> orderMapper = new OrderRowMapper();
  private final RowMapper<User> userMapper = new UserRowMapper();
  private final RowMapper<MisoRun> runMapper = new RunRowMapper();
  
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
    // TODO Auto-generated method stub
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
    List<MisoOrder> orders = template.query(queryAllOrders, orderMapper);
    ChildMapper<MisoOrder, MisoOrderSample> mapper = new ChildMapper<>(orders);
    mapper.mapChildren(getOrderSamples());
    return new ArrayList<Order>(orders);
  }

  @Override
  public Order getOrder(Integer id) {
    List<MisoOrder> orders = template.query(queryOrderById, new Object[]{id}, orderMapper);
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
    List<MisoRun> runs = template.query(queryAllRuns, runMapper);
    ChildMapper<MisoRun, MisoRunPosition> mapper = new ChildMapper<>(runs);
    mapper.mapChildren(getRunPositions());
    return new ArrayList<Run>(runs);
  }

  @Override
  public Run getRun(Integer id) {
    List<MisoRun> runs = template.query(queryRunById, new Object[]{id}, runMapper);
    if (runs.size() != 1) return null;
    MisoRun run = runs.get(0);
    Set<RunPosition> rp = new HashSet<>();
    rp.addAll(getRunPositions(id));
    run.setSample(rp);
    return run;
  }

  @Override
  public Run getRun(String runName) {
    List<MisoRun> runs = template.query(queryRunByName, new Object[]{runName}, runMapper);
    if (runs.size() != 1) return null;
    MisoRun run = runs.get(0);
    Set<RunPosition> rp = new HashSet<>();
    rp.addAll(getRunPositions(run.getId()));
    run.setSample(rp);
    return run;
  }
  
  private List<MisoRunPosition> getRunPositions() {
    // TODO
    return new ArrayList<MisoRunPosition>();
  }
  
  private List<MisoRunPosition> getRunPositions(Integer runId) {
    // TODO
    return new ArrayList<MisoRunPosition>();
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
  
  private static class OrderRowMapper implements RowMapper<MisoOrder> {

    @Override
    public MisoOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoOrder o = new MisoOrder();
      
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
  
  private static class RunRowMapper implements RowMapper<MisoRun> {

    @Override
    public MisoRun mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRun r = new MisoRun();
      
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
  
}
