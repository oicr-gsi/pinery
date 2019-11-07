package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.lims.DefaultChangeLog;
import ca.on.oicr.pinery.lims.flatfile.model.FileChange;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ChangeFileDao implements ChangeDao {

  private static final String queryAllChanges = "SELECT * FROM changes";

  private static final String queryChangesBySampleId = queryAllChanges + " WHERE sampleId LIKE ?";

  private static final RowMapper<FileChange> changeMapper =
      new RowMapper<FileChange>() {

        @Override
        public FileChange mapRow(ResultSet rs, int rowNum) throws SQLException {
          FileChange c = new FileChange(rs.getString("sampleId"));
          c.setAction(rs.getString("action"));
          c.setCreated(ModelUtils.convertToDate(rs.getString("createdDate")));
          int creator = rs.getInt("createdUserId");
          if (creator != 0) c.setCreatedById(creator);
          return c;
        }
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<ChangeLog> getAllChanges() {
    List<FileChange> allChanges = template.query(queryAllChanges, changeMapper);
    return compileLogs(allChanges);
  }

  @Override
  public ChangeLog getSampleChanges(String sampleId) {
    // don't validate sampleID because it could be from any LIMS
    List<FileChange> changes =
        template.query(queryChangesBySampleId, new Object[] {sampleId}, changeMapper);
    return DaoUtils.getExpectedSingleResult(compileLogs(changes));
  }

  private static List<ChangeLog> compileLogs(List<FileChange> changes) {
    Map<String, ChangeLog> map = new HashMap<>();
    for (FileChange change : changes) {
      ChangeLog log = map.get(change.getSampleId());
      if (log == null) {
        log = new DefaultChangeLog();
        log.setSampleId(change.getSampleId());
        log.setChanges(new HashSet<Change>());
        map.put(log.getSampleId(), log);
      }
      log.getChanges().add(change);
    }
    return new ArrayList<>(map.values());
  }
}
