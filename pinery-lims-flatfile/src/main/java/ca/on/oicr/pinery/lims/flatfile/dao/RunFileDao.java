package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;

public class RunFileDao implements RunDao {
  
  private static final String queryAllRuns = "SELECT * FROM runs";
  
  private static final String queryRunById = queryAllRuns
      + " WHERE id LIKE ?";
  
  private static final String queryRunByName = queryAllRuns
      + " WHERE name LIKE ?";
  
  private static final RowMapper<Run> runMapper = new RowMapper<Run>() {

    @Override
    public Run mapRow(ResultSet rs, int rowNum) throws SQLException {
      Run r = new DefaultRun();
      
      r.setId(rs.getInt("id"));
      r.setName(rs.getString("name"));
      r.setInstrumentId(rs.getInt("instrumentId"));
      r.setState(rs.getString("state"));
      r.setBarcode(rs.getString("barcode"));
      
      r.setSample(parseRunPositions(rs.getString("positions")));
      
      return r;
    }
    
    private Set<RunPosition> parseRunPositions(String positionsString) {
      if (positionsString == null || positionsString.length() == 0) return null;
      Set<RunPosition> positions = new HashSet<>();
      List<String> positionStrings = DaoUtils.parseList(positionsString);
      for (String positionString : positionStrings) {
        Map<String, String> map = DaoUtils.parseKeyValuePairs(positionString);
        RunPosition pos = new DefaultRunPosition();
        pos.setPosition(Integer.parseInt(map.get("position")));
        pos.setRunSample(parseRunSamples(map.get("samples")));
        positions.add(pos);
      }
      return positions;
    }
    
    private Set<RunSample> parseRunSamples(String samplesString) {
      List<String> sampleStrings = DaoUtils.parseList(samplesString);
      if (samplesString == null || samplesString.length() == 0) return null;
      Set<RunSample> samples = new HashSet<>();
      for (String sampleString : sampleStrings) {
        Map<String, String> sampleMap = DaoUtils.parseKeyValuePairs(sampleString);
        RunSample sample = new DefaultRunSample();
        sample.setId(Integer.parseInt(sampleMap.get("id")));
        if (sampleMap.containsKey("barcode")) sample.setBarcode(sampleMap.get("barcode"));
        if (sampleMap.containsKey("barcodeTwo")) sample.setBarcodeTwo(sampleMap.get("barcodeTwo"));
        samples.add(sample);
      }
      return samples;
    }
    
  };
  
  @Autowired
  private JdbcTemplate template;
  
  @Override
  public List<Run> getAllRuns() {
    return template.query(queryAllRuns, runMapper);
  }

  @Override
  public Run getRun(Integer id) {
    List<Run> runs = template.query(queryRunById, new Object[]{id}, runMapper);
    return DaoUtils.getExpectedSingleResult(runs);
  }

  @Override
  public Run getRun(String name) {
    List<Run> runs = template.query(queryRunByName, new Object[]{name}, runMapper);
    return DaoUtils.getExpectedSingleResult(runs);
  }

}
