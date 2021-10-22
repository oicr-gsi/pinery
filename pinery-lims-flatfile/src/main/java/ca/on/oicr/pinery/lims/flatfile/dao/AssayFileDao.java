package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Assay;
import ca.on.oicr.pinery.api.AssayMetric;
import ca.on.oicr.pinery.api.AssayMetricSubcategory;
import ca.on.oicr.pinery.lims.DefaultAssay;
import ca.on.oicr.pinery.lims.DefaultAssayMetric;
import ca.on.oicr.pinery.lims.DefaultAssayMetricSubcategory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AssayFileDao implements AssayDao {

  private static final String queryAllAssays = "SELECT * FROM assays";
  private static final String queryAssayById = queryAllAssays + " WHERE id LIKE ?";

  private static final RowMapper<Assay> mapper =
      (rs, rowNum) -> {
        Assay assay = new DefaultAssay();
        assay.setId(rs.getInt("id"));
        assay.setName(rs.getString("name"));
        assay.setDescription(rs.getString("description"));
        assay.setVersion(rs.getString("version"));
        parseMetrics(rs.getString("metrics"), assay);
        return assay;
      };

  @Autowired private JdbcTemplate template;

  @Override
  public List<Assay> getAll() {
    return template.query(queryAllAssays, mapper);
  }

  @Override
  public Assay get(Integer id) {
    List<Assay> assays = template.query(queryAssayById, new Object[] {id}, mapper);
    return DaoUtils.getExpectedSingleResult(assays);
  }

  private static void parseMetrics(String string, Assay assay) {
    List<String> metricStrings = DaoUtils.parseList(string);
    for (String metricString : metricStrings) {
      Map<String, String> map = DaoUtils.parseKeyValuePairs(metricString);
      AssayMetric metric = new DefaultAssayMetric();
      metric.setName(map.get("name"));
      metric.setCategory(map.get("category"));
      if (map.containsKey("subcategoryName")) {
        AssayMetricSubcategory subcat = new DefaultAssayMetricSubcategory();
        subcat.setName(map.get("subcategoryName"));
        subcat.setSortPriority(getIntOrNull(map, "subcategorySortPriority"));
        subcat.setDesignCode(map.get("subcategoryDesignCode"));
        metric.setSubcategory(subcat);
      }
      metric.setUnits(map.get("units"));
      metric.setThresholdType(map.get("thresholdType"));
      metric.setMinimum(getBigDecimalOrNull(map, "minimum"));
      metric.setMaximum(getBigDecimalOrNull(map, "maximum"));
      metric.setNucleicAcidType(map.get("nucleicAcidType"));
      metric.setTissueMaterial(map.get("tissueMaterial"));
      metric.setTissueType(map.get("tissueType"));
      metric.setNegateTissueType(getBooleanOrNull(map, "negateTissueType"));
      metric.setTissueOrigin(map.get("tissueOrigin"));
      metric.setContainerModel(map.get("containerModel"));
      metric.setReadLength(getIntOrNull(map, "readLength"));
      metric.setReadLength2(getIntOrNull(map, "readLength2"));
      assay.addMetric(metric);
    }
  }

  private static Integer getIntOrNull(Map<String, String> map, String key) {
    if (map.containsKey(key)) {
      return Integer.parseInt(map.get(key));
    } else {
      return null;
    }
  }

  private static BigDecimal getBigDecimalOrNull(Map<String, String> map, String key) {
    if (map.containsKey(key)) {
      return new BigDecimal(map.get(key));
    } else {
      return null;
    }
  }

  private static Boolean getBooleanOrNull(Map<String, String> map, String key) {
    if (map.containsKey(key)) {
      return Boolean.parseBoolean(map.get(key));
    } else {
      return null;
    }
  }
}
