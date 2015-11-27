package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;

public class InstrumentFileDao implements InstrumentDao {
  
  private static final String queryAllModels = "SELECT DISTINCT modelId, modelName" +
      " FROM instruments";
  
  private static final String queryModelById = queryAllModels +
      " WHERE modelId LIKE ?";
  
  @Autowired
  private JdbcTemplate template;
  
  private final RowMapper<InstrumentModel> modelMapper = new RowMapper<InstrumentModel>() {

    @Override
    public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      InstrumentModel m = new DefaultInstrumentModel();
      
      m.setId(rs.getInt("modelId"));
      m.setName(rs.getString("modelName"));
      // TODO: create/mod user/dates
      
      return m;
    }
    
  };
  
  @Override
  public List<InstrumentModel> getAllInstrumentModels() {
    return template.query(queryAllModels, modelMapper);
  }
  
  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    List<InstrumentModel> models = template.query(queryModelById, new Object[]{id}, modelMapper);
    switch (models.size()) {
    case 0:
      return null;
    case 1:
      return models.get(0);
    default:
      throw new RuntimeException(); // TODO: better exception type
    }
  }
  
}
