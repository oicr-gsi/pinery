package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SampleProjectFileDao implements SampleProjectDao {

  private static final String queryProjectList = "SELECT * FROM projects";
  private static final String querySampleStats =
      "SELECT projectName, COUNT(*) AS count,"
          + " COUNT(CASE archived WHEN 'true' THEN 1 ELSE NULL END) As archivedCount,"
          + " MIN(createdDate) AS earliest, MAX(modifiedDate) as latest"
          + " FROM samples"
          + " GROUP BY projectName";

  private static final RowMapper<SampleProject> projectMapper =
      new RowMapper<SampleProject>() {

        @Override
        public SampleProject mapRow(ResultSet rs, int rowNum) throws SQLException {
          SampleProject p = new DefaultSampleProject();
          p.setName(rs.getString("projectName"));
          p.setActive(rs.getBoolean("active"));
          p.setPipeline(rs.getString("pipeline"));
          p.setSecondaryNamingScheme(rs.getBoolean("secondaryNamingScheme"));
          p.setCreated(ModelUtils.convertToDate(rs.getString("created")));
          p.setRebNumber(ModelUtils.nullIfEmpty(rs.getString("rebNumber")));
          p.setRebExpiry(ModelUtils.convertToDate(rs.getString("rebExpiry")));
          p.setDescription(ModelUtils.nullIfEmpty(rs.getString("description")));
          String samplesExpected = rs.getString("samplesExpected");
          if (samplesExpected != null && !samplesExpected.isEmpty()) {
            p.setSamplesExpected(Integer.parseInt(samplesExpected));
          }
          p.setContactName(ModelUtils.nullIfEmpty(rs.getString("contactName")));
          p.setContactEmail(ModelUtils.nullIfEmpty(rs.getString("contactEmail")));
          return p;
        }
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<SampleProject> getAllSampleProjects() {
    List<SampleProject> projects = template.query(queryProjectList, projectMapper);
    Map<String, SampleProject> projectsByName =
        projects.stream().collect(Collectors.toMap(SampleProject::getName, Function.identity()));
    template.query(
        querySampleStats,
        rs -> {
          SampleProject project = projectsByName.get(rs.getString("projectName"));
          if (project == null) {
            project = new DefaultSampleProject();
            project.setName(rs.getString("projectName"));
            project.setActive(false);
            projects.add(project);
            projectsByName.put(project.getName(), project);
          }
          project.setCount(rs.getInt("count"));
          project.setArchivedCount(rs.getInt("archivedCount"));
          project.setEarliest(ModelUtils.convertToDate(rs.getString("earliest")));
          project.setLatest(ModelUtils.convertToDate(rs.getString("latest")));
        });
    return projects;
  }
}
