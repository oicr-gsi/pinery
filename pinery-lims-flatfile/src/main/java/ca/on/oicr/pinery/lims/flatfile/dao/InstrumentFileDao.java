package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class InstrumentFileDao implements InstrumentDao {

  private static final String queryAllModels =
      "SELECT DISTINCT modelId, modelName,"
          + " modelCreatedDate AS createdDate, modelCreatedUserId AS createdUserId,"
          + " modelModifiedDate AS modifiedDate, modelModifiedUserId AS modifiedUserId, modelPlatform AS platform"
          + " FROM instruments";

  private static final String queryModelById = queryAllModels + " WHERE modelId LIKE ?";

  private static final String queryAllInstruments =
      "SELECT id, name, createdDate, modelId" + " FROM instruments";

  private static final String queryInstrumentById = queryAllInstruments + " WHERE id LIKE ?";

  private static final String queryInstrumentsByModelId =
      queryAllInstruments + " WHERE modelId LIKE ?";

  private static final RowMapper<InstrumentModel> modelMapper =
      new RowMapper<InstrumentModel>() {

        @Override
        public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
          InstrumentModel m = new DefaultInstrumentModel();

          m.setId(rs.getInt("modelId"));
          m.setName(rs.getString("modelName"));
          m.setPlatform(rs.getString("platform"));
          m.setCreated(ModelUtils.convertToDate(rs.getString("createdDate")));
          m.setCreatedById(ModelUtils.nullIfZero(rs.getInt("createdUserId")));
          m.setModified(ModelUtils.convertToDate(rs.getString("modifiedDate")));
          m.setModifiedById(ModelUtils.nullIfZero(rs.getInt("modifiedUserId")));

          return m;
        }
      };

  private static final RowMapper<Instrument> instrumentMapper =
      new RowMapper<Instrument>() {

        @Override
        public Instrument mapRow(ResultSet rs, int rowNum) throws SQLException {
          Instrument i = new DefaultInstrument();

          i.setId(rs.getInt("id"));
          i.setName(rs.getString("name"));
          i.setCreated(ModelUtils.convertToDate(rs.getString("createdDate")));
          i.setModelId(rs.getInt("modelId"));

          return i;
        }
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<InstrumentModel> getAllInstrumentModels() {
    return template.query(queryAllModels, modelMapper);
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    List<InstrumentModel> models = template.query(queryModelById, new Object[] {id}, modelMapper);
    return DaoUtils.getExpectedSingleResult(models);
  }

  @Override
  public List<Instrument> getAllInstruments() {
    return template.query(queryAllInstruments, instrumentMapper);
  }

  @Override
  public Instrument getInstrument(Integer id) {
    List<Instrument> instruments =
        template.query(queryInstrumentById, new Object[] {id}, instrumentMapper);
    return DaoUtils.getExpectedSingleResult(instruments);
  }

  @Override
  public List<Instrument> getInstrumentModelInstruments(Integer modelId) {
    return template.query(queryInstrumentsByModelId, new Object[] {modelId}, instrumentMapper);
  }
}
