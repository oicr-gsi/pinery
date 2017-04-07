package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.BoxPosition;
import ca.on.oicr.pinery.lims.DefaultBox;
import ca.on.oicr.pinery.lims.DefaultBoxPosition;

import com.google.common.collect.Sets;

public class BoxFileDao implements BoxDao {
  
  private static final String queryAllBoxes = "SELECT * FROM boxes";
  
  private static final RowMapper<Box> boxMapper = new RowMapper<Box>() {

    @Override
    public Box mapRow(ResultSet rs, int rowNum) throws SQLException {
      Box box = new DefaultBox();
      box.setId(rs.getLong("id"));
      box.setName(rs.getString("name"));
      box.setDescription(rs.getString("description"));
      box.setLocation(rs.getString("location"));
      box.setRows(rs.getInt("rows"));
      box.setColumns(rs.getInt("columns"));
      box.setPositions(parsePositions(rs.getString("samples")));
      return box;
    }
    
    private Set<BoxPosition> parsePositions(String positions) {
      Set<BoxPosition> result = Sets.newHashSet();
      for (String position : DaoUtils.parseList(positions)) {
        Map<String, String> pairs = DaoUtils.parseKeyValuePairs(position);
        result.add(makeBoxPosition(pairs.get("position"), pairs.get("sampleId")));
      }
      return result;
    }
    
    private BoxPosition makeBoxPosition(String position, String sampleId) {
      BoxPosition p = new DefaultBoxPosition();
      p.setPosition(position);
      p.setSampleId(sampleId);
      return p;
    }
    
  };
  
  @Autowired
  private JdbcTemplate template;

  @Override
  public List<Box> getAllBoxes() {
    return template.query(queryAllBoxes, boxMapper);
  }

}
