package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Status;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultAttributeName;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultStatus;
import ca.on.oicr.pinery.lims.DefaultType;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

public class SampleFileDao implements SampleDao {

  private static final String queryAllSamples = "SELECT * FROM samples";
  
  private static final String queryFilteredSamples = queryAllSamples
      + " WHERE archived LIKE ?"
      + " AND createdDate < ?"
      + " AND createdDate > ?";
  
  private static final String querySampleById = queryAllSamples
      + " WHERE id LIKE ?";
  
  private static final String queryProjectList = "SELECT projectName, COUNT(*) AS count,"
      + " COUNT(CASE archived WHEN 'true' THEN 1 ELSE NULL END) As archivedCount,"
      + " MIN(createdDate) AS earliest, MAX(modifiedDate) as latest"
      + " FROM samples"
      + " GROUP BY projectName";
  
  private static final String queryTypeList = "SELECT sampleType, COUNT(*) AS count,"
      + " COUNT(CASE archived WHEN 'true' THEN 1 ELSE NULL END) As archivedCount,"
      + " MIN(createdDate) AS earliest, MAX(modifiedDate) as latest"
      + " FROM samples"
      + " GROUP BY sampleType";
  
  private static final RowMapper<SampleProject> projectMapper = new RowMapper<SampleProject>() {

    @Override
    public SampleProject mapRow(ResultSet rs, int rowNum) throws SQLException {
      SampleProject p = new DefaultSampleProject();
      p.setName(rs.getString("projectName"));
      p.setCount(rs.getInt("count"));
      p.setArchivedCount(rs.getInt("archivedCount"));
      p.setEarliest(ModelUtils.convertToDate(rs.getString("earliest")));
      p.setLatest(ModelUtils.convertToDate(rs.getString("latest")));
      return p;
    }
    
  };
  
  private static final RowMapper<Type> typeMapper = new RowMapper<Type>() {

    @Override
    public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
      Type t = new DefaultType();
      t.setName(rs.getString("sampleType"));
      t.setCount(rs.getInt("count"));
      t.setArchivedCount(rs.getInt("archivedCount"));
      t.setEarliest(ModelUtils.convertToDate(rs.getString("earliest")));
      t.setLatest(ModelUtils.convertToDate(rs.getString("latest")));
      return t;
    }
    
  };
  
  private static final RowMapper<Sample> sampleMapper = new RowMapper<Sample>() {

    @Override
    public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
      Sample s = new DefaultSample();
      
      s.setId(rs.getInt("id"));
      s.setName(rs.getString("name"));
      s.setDescription(rs.getString("description"));
      s.setTubeBarcode(rs.getString("tubeBarcode"));
      s.setStorageLocation(rs.getString("storageLocation"));
      s.setSampleType(rs.getString("sampleType"));
      
      s.setCreated(ModelUtils.convertToDate(rs.getString("createdDate")));
      int creator = rs.getInt("createdUserId");
      if (creator != 0) s.setCreatedById(creator);
      
      s.setModified(ModelUtils.convertToDate(rs.getString("modifiedDate")));
      int modifier = rs.getInt("modifiedUserId");
      if (modifier != 0) s.setModifiedById(modifier);
      
      int parentId = rs.getInt("parentSampleId");
      if (parentId != 0) {
        Set<Integer> parents = new HashSet<>();
        parents.add(parentId);
        s.setParents(parents);
      }
      
      s.setProject(rs.getString("projectName"));
      s.setArchived(rs.getBoolean("archived"));
      
      s.setStatus(parseStatus(rs.getString("status")));
      s.setAttributes(parseAttributes(rs.getString("attributes")));
      
      return s;
    }
    
    private Status parseStatus(String string) {
      Map<String, String> map = ModelUtils.parseKeyValuePairs(string);
      if (map.isEmpty())  return null;
      
      Status status = new DefaultStatus();
      status.setName(map.get("name"));
      status.setState(map.get("state"));
      return status;
    }
    
    private Set<Attribute> parseAttributes(String string) {
      Map<String, String> attributeMap = ModelUtils.parseKeyValuePairs(string);
      
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
  
  @Autowired
  private JdbcTemplate template;
  
  @Override
  public Sample getSample(Integer id) {
    List<Sample> samples = template.query(querySampleById,  new Object[]{id}, sampleMapper);
    return DaoUtils.getExpectedSingleResult(samples);
  }

  @Override
  public List<Sample> getAllSamples() {
    return template.query(queryAllSamples, sampleMapper);
  }

  @Override
  public List<Sample> getSamplesFiltered(Boolean archived,
      Set<String> projects, Set<String> types, DateTime before, DateTime after) {
    String fullQuery = queryFilteredSamples 
        + makeProjectsClause(projects == null ? 0 : projects.size())
        + makeTypesClause(types == null ? 0 : types.size());
    
    Object[] params = makeQueryParams(archived, projects, types, before, after);
    return template.query(fullQuery, params, sampleMapper);
  }
  
  private Object[] makeQueryParams(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after) {
    int paramCount = 3 + (projects == null ? 0 : projects.size()) + (types == null ? 0 : types.size());
    Object[] queryParams = new Object[paramCount];
    queryParams[0] = archived == null ? "%" : archived.toString();
    queryParams[1] = before == null ? DateTime.now().plusDays(1).toString() : before.toString();
    queryParams[2] = after == null ? DateTime.now().withYear(2000).toString() : after.toString();
    int paramPos = 3;
    for (String project : projects) {
      queryParams[paramPos++] = project;
    }
    for (String type : types) {
      queryParams[paramPos++] = type;
    }
    return queryParams;
  }
  
  private static String makeProjectsClause(int projectParamCount) {
    if (projectParamCount == 0) return "";
    
    StringBuilder sb = new StringBuilder();
    sb.append(" AND (");
    boolean firstItem = true;
    for (int i = 0; i < projectParamCount; i++) {
      if (!firstItem) sb.append(" OR ");
      else firstItem = false;
      
      sb.append("projectName LIKE ?");
    }
    sb.append(")");
    return sb.toString();
  }
  
  private static String makeTypesClause(int typeParamCount) {
    if (typeParamCount == 0) return "";
    
    StringBuilder sb = new StringBuilder();
    sb.append(" AND (");
    boolean firstItem = true;
    for (int i = 0; i < typeParamCount; i++) {
      if (!firstItem) sb.append(" OR ");
      else firstItem = false;
      
      sb.append("sampleType LIKE ?");
    }
    sb.append(")");
    return sb.toString();
  }

  @Override
  public List<SampleProject> getAllSampleProjects() {
    return template.query(queryProjectList, projectMapper);
  }

  @Override
  public List<AttributeName> getAllSampleAttributes() {
    List<Sample> samples = getAllSamples();
    
    Map<String, AttributeName> map = new HashMap<>();
    for (Sample sample : samples) {
      for (Attribute att : sample.getAttributes()) {
        AttributeName an = map.get(att.getName());
        if (an == null) {
          an = new DefaultAttributeName();
          an.setName(att.getName());
          an.setCount(1);
          an.setArchivedCount(sample.getArchived() ? 1 : 0);
          an.setEarliest(sample.getCreated());
          an.setLatest(sample.getModified());
          map.put(an.getName(), an);
        }
        else {
          an.setCount(an.getCount() + 1);
          if (sample.getArchived()) an.setArchivedCount(an.getArchivedCount() + 1);
          if (sample.getCreated().before(an.getEarliest())) an.setEarliest(sample.getCreated());
          if (sample.getModified().after(an.getLatest())) an.setLatest(sample.getModified());
        }
      }
    }
    
    return new ArrayList<AttributeName>(map.values());
  }

  @Override
  public List<Type> getAllSampleTypes() {
    return template.query(queryTypeList, typeMapper);
  }

}
