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
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultOrder;

public class MisoClient implements Lims {
  
  // InstrumentModel queries
  private static final String queryAllModels = "SELECT p.platformId, p.instrumentModel " +
      "FROM Platform as p";
  private static final String queryModelById = queryAllModels + " WHERE platformId = ?";
  
  // Instrument queries
  private static final String queryAllInstruments = "SELECT sr.referenceId, sr.name, sr.platformId " + 
      "FROM SequencerReference AS sr";
  private static final String queryInstrumentById = queryAllInstruments + " WHERE referenceId = ?";
  private static final String queryInstrumentsByModelId = queryAllInstruments + " WHERE platformId = ?";
  
  // Order queries
  private static final String queryAllOrders = "SELECT pool.ready, prj.name AS project, plat.name AS platform, pool.poolId " +
      "FROM Pool AS pool " +
      "JOIN Experiment AS ex ON ex.experimentId = pool.experiment_experimentId " +
      "JOIN Study AS st ON st.studyId = ex.study_studyId " +
      "JOIN Project AS prj ON prj.projectId = st.project_projectId " +
      "JOIN Platform AS plat ON plat.platformId = ex.platform_platformId";
  private static final String queryOrderById = queryAllOrders + " WHERE poolId = ?";
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelMapper();
  private final RowMapper<Order> orderMapper = new OrderMapper();
  
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUser(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Order> getOrders() {
    List<Order> orders = template.query(queryAllOrders, orderMapper);
    List<ContainedOrderSample> samples = getOrderSamples();
    Map<Integer, Order> map = new HashMap<>();
    for (Order o : orders) {
      map.put(o.getId(), o);
    }
    for (ContainedOrderSample s : samples) {
      Order o = map.get(s.getId());
      if (o != null) {
        Set<OrderSample> os = o.getSamples();
        if (os == null) os = new HashSet<>();
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
    List<ContainedOrderSample> samples = getOrderSamples(id);
    Set<OrderSample> os = new HashSet<>();
    for (OrderSample s : samples) {
      os.add(s);
    }
    order.setSample(os);
    return order;
  }
  
  private List<ContainedOrderSample> getOrderSamples() {
    // TODO: get all samples that are linked to any order
    return new ArrayList<ContainedOrderSample>();
  }
  
  private List<ContainedOrderSample> getOrderSamples(Integer orderId) {
    // TODO: get all samples with this order id (poolId)
    return new ArrayList<ContainedOrderSample>();
  }

  @Override
  public List<Run> getRuns() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Run getRun(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Run getRun(String runName) {
    // TODO Auto-generated method stub
    return null;
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
  
  private class InstrumentMapper implements RowMapper<Instrument> {

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
  
  private class InstrumentModelMapper implements RowMapper<InstrumentModel> {

    @Override
    public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      InstrumentModel m = new DefaultInstrumentModel();
      
      m.setId(rs.getInt("platformId"));
      m.setName(rs.getString("instrumentModel"));
      // TODO: created, createdById, modified, modifiedById
      
      return m;
    }
    
  }
  
  private class OrderMapper implements RowMapper<Order> {

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

}
