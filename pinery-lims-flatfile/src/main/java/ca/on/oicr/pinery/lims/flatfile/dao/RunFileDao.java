package ca.on.oicr.pinery.lims.flatfile.dao;

import static ca.on.oicr.pinery.lims.flatfile.dao.DaoUtils.getStringIfPresent;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Status;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.lims.DefaultStatus;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class RunFileDao implements RunDao {

  private static final String queryAllRuns = "SELECT * FROM runs";

  private static final String queryRunById = queryAllRuns + " WHERE id LIKE ?";

  private static final String queryRunByName = queryAllRuns + " WHERE name LIKE ?";

  private static final RowMapper<Run> runMapper =
      new RowMapper<Run>() {

        @Override
        public Run mapRow(ResultSet rs, int rowNum) throws SQLException {
          Run r = new DefaultRun();

          r.setId(rs.getInt("id"));
          r.setName(ModelUtils.nullIfEmpty(rs.getString("name")));
          r.setStartDate(ModelUtils.convertToDate(rs.getString("startDate")));
          r.setCompletionDate(ModelUtils.convertToDate(rs.getString("completionDate")));
          r.setCreatedDate(ModelUtils.convertToDate(rs.getString("createdDate")));
          r.setCreatedById(ModelUtils.nullIfZero(rs.getInt("createdUserId")));
          r.setModified(ModelUtils.convertToDate(rs.getString("modifiedDate")));
          r.setModifiedById(ModelUtils.nullIfZero(rs.getInt("modifiedUserId")));
          r.setInstrumentId(ModelUtils.nullIfZero(rs.getInt("instrumentId")));
          r.setInstrumentName(ModelUtils.nullIfEmpty(rs.getString("instrumentName")));
          r.setState(ModelUtils.nullIfEmpty(rs.getString("state")));
          r.setBarcode(ModelUtils.nullIfEmpty(rs.getString("barcode")));
          r.setRunDirectory(ModelUtils.nullIfEmpty(rs.getString("runDirectory")));
          r.setRunBasesMask(ModelUtils.nullIfEmpty(rs.getString("runBasesMask")));
          r.setSequencingParameters(ModelUtils.nullIfEmpty(rs.getString("sequencingParameters")));
          r.setChemistry(ModelUtils.nullIfEmpty(rs.getString("chemistry")));
          r.setWorkflowType(getStringIfPresent(rs, "workflowType"));
          r.setContainerModel(getStringIfPresent(rs, "containerModel"));
          r.setSequencingKit(getStringIfPresent(rs, "sequencingKit"));
          r.setStatus(ModelUtils.parseStatus(rs.getString("status")));
          r.setDataReview(ModelUtils.parseBooleanOrNull("dataReview"));
          r.setDataReviewDate(ModelUtils.convertToLocalDate(rs.getString("dataReviewDate")));

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
            pos.setPoolId(ModelUtils.parseIntOrNull(map.get("poolId")));
            pos.setPoolName(map.get("poolName"));
            pos.setPoolBarcode(map.get("poolBarcode"));
            pos.setPoolDescription(map.get("poolDescription"));

            if (map.containsKey("poolStatusName") || map.containsKey("poolStatusState")) {
              Status status = new DefaultStatus();
              status.setName(map.get("poolStatusName"));
              status.setState(map.get("poolStatusState"));
              pos.setPoolStatus(status);
            }

            pos.setPoolCreatedById(ModelUtils.parseIntOrNull(map.get("poolCreatedById")));
            pos.setPoolCreated(ModelUtils.convertToDate(map.get("poolCreated")));
            pos.setPoolModifiedById(ModelUtils.parseIntOrNull(map.get("poolModifiedById")));
            pos.setPoolModified(ModelUtils.convertToDate(map.get("poolModified")));
            pos.setQcStatus(map.get("qcStatus"));
            pos.setAnalysisSkipped(ModelUtils.parseBooleanOrNull(map.get("analysisSkipped")));
            pos.setRunPurpose(ModelUtils.nullIfEmpty(map.get("runPurpose")));
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
            sample.setId((sampleMap.get("id")));
            sample.setBarcode(ModelUtils.nullIfEmpty(sampleMap.get("barcode")));
            sample.setBarcodeTwo(ModelUtils.nullIfEmpty(sampleMap.get("barcodeTwo")));
            sample.setRunPurpose(ModelUtils.nullIfEmpty(sampleMap.get("runPurpose")));
            if (sampleMap.containsKey("attributes")) {
              sample.setAttributes(parseAttributes(sampleMap.get("attributes")));
            }
            if (sampleMap.containsKey("statusName") || sampleMap.containsKey("statusState")) {
              Status status = new DefaultStatus();
              status.setName(sampleMap.get("statusName"));
              status.setState(sampleMap.get("statusState"));
              status.setDate(ModelUtils.convertToLocalDate(sampleMap.get("statusDate")));
              sample.setStatus(status);
            }
            samples.add(sample);
          }
          return samples;
        }

        private Set<Attribute> parseAttributes(String string) {
          Map<String, String> attributeMap = DaoUtils.parseKeyValuePairs(string);

          Set<Attribute> attributes = new HashSet<>();
          for (String key : attributeMap.keySet()) {
            Attribute att = new DefaultAttribute();
            att.setName(key);
            att.setValue(attributeMap.get(key));
            attributes.add(att);
          }
          return attributes;
        }
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<Run> getAllRuns() {
    return template.query(queryAllRuns, runMapper);
  }

  @Override
  public Run getRun(Integer id) {
    List<Run> runs = template.query(queryRunById, new Object[] {id}, runMapper);
    return DaoUtils.getExpectedSingleResult(runs);
  }

  @Override
  public Run getRun(String name) {
    List<Run> runs = template.query(queryRunByName, new Object[] {name}, runMapper);
    return DaoUtils.getExpectedSingleResult(runs);
  }
}
