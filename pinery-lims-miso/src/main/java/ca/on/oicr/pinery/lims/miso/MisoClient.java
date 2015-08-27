package ca.on.oicr.pinery.lims.miso;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;

public class MisoClient implements Lims {
  
  private static final String queryAllInstruments = "SELECT sr.referenceId, sr.name, sr.platformId " + 
      "FROM SequencerReference AS sr";
  
  private static final String queryInstrumentById = queryAllInstruments + " WHERE referenceId = ?";
  
  private static final String queryAllModels = "SELECT p.platformId, p.instrumentModel " +
      "FROM Platform as p";
  
  private static final String queryModelById = queryAllModels + " WHERE platformId = ?";
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelMapper();
  
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Order getOrder(Integer id) {
    // TODO Auto-generated method stub
    return null;
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
    List<InstrumentModel> models = template.query(queryAllModels, modelMapper);
    return models;
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    List<InstrumentModel> models = template.query(queryModelById, new Object[]{id}, modelMapper);
    return models.size() == 1 ? models.get(0) : null;
  }

  @Override
  public List<Instrument> getInstruments() {
    List<Instrument> instruments = template.query(queryAllInstruments, instrumentMapper);
    return instruments;
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    List<Instrument> instruments = template.query(queryInstrumentById, new Object[]{instrumentId}, instrumentMapper);
    return instruments.size() == 1 ? instruments.get(0) : null;
  }

  @Override
  public List<Instrument> getInstrumentModelInsrument(Integer id) {
    // TODO Auto-generated method stub
    return null;
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

}
