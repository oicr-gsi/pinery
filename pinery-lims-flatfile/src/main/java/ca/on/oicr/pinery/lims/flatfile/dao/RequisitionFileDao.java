package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.api.SignOff;
import ca.on.oicr.pinery.lims.DefaultRequisition;
import ca.on.oicr.pinery.lims.DefaultSignOff;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class RequisitionFileDao implements RequisitionDao {

  private static final String queryAllRequisitions = "SELECT * FROM requisitions";
  private static final String queryRequisitionById = queryAllRequisitions + " WHERE id LIKE ?";
  private static final String queryRequisitionByName = queryAllRequisitions + " WHERE name = ?";

  private static final RowMapper<Requisition> mapper =
      (rs, rowNum) -> {
        Requisition req = new DefaultRequisition();
        req.setId(rs.getInt("id"));
        req.setName(rs.getString("name"));
        req.setAssayId(ModelUtils.parseIntOrNull(rs.getString("assayId")));
        parseSampleIds(rs.getString("sampleIds"), req);
        parseSignOffs(rs.getString("signOffs"), req);
        return req;
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<Requisition> getAll() {
    return template.query(queryAllRequisitions, mapper);
  }

  @Override
  public Requisition get(Integer id) {
    List<Requisition> reqs = template.query(queryRequisitionById, new Object[] {id}, mapper);
    return DaoUtils.getExpectedSingleResult(reqs);
  }

  @Override
  public Requisition getByName(String name) {
    List<Requisition> reqs = template.query(queryRequisitionByName, new Object[] {name}, mapper);
    return DaoUtils.getExpectedSingleResult(reqs);
  }

  private static void parseSampleIds(String string, Requisition requisition) {
    List<String> sampleIds = DaoUtils.parseList(string);
    for (String sampleId : sampleIds) {
      requisition.addSampleId(sampleId);
    }
  }

  private static void parseSignOffs(String string, Requisition requisition) {
    List<String> soStrings = DaoUtils.parseList(string);
    for (String soString : soStrings) {
      Map<String, String> map = DaoUtils.parseKeyValuePairs(soString);
      SignOff so = new DefaultSignOff();
      so.setName(map.get("name"));
      so.setPassed(Boolean.valueOf(map.get("passed")));
      so.setDate(ModelUtils.convertToLocalDate(map.get("date")));
      so.setUserId(Integer.valueOf(map.get("userId")));
      requisition.addSignOff(so);
    }
  }
}
