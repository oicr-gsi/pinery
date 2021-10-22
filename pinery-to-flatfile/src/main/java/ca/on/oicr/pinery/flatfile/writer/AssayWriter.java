package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.AssayDto;
import ca.on.oicr.ws.dto.AssayMetricDto;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AssayWriter extends Writer {

  private static final String[] headers = {"id", "name", "description", "version", "metrics"};

  private final List<AssayDto> assays;

  public AssayWriter(List<AssayDto> assays) {
    this.assays = assays;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return assays.size();
  }

  @Override
  protected String[] getRecord(int row) {
    AssayDto assay = assays.get(row);

    return new String[] {
      assay.getId().toString(),
      assay.getName(),
      Objects.toString(assay.getDescription(), ""),
      assay.getVersion(),
      getMetricsString(assay.getMetrics())
    };
  }

  private static String getMetricsString(Set<AssayMetricDto> metrics) {
    ArrayStringBuilder sb = new ArrayStringBuilder();
    for (AssayMetricDto metric : metrics) {
      sb.append(getMetricString(metric));
    }
    return sb.toString();
  }

  private static String getMetricString(AssayMetricDto metric) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    sb.append("name", metric.getName());
    sb.append("category", metric.getCategory());
    if (metric.getSubcategory() != null) {
      sb.appendNonNull("subcategoryName", metric.getSubcategory().getName());
      sb.appendNonNull("subcategorySortPriority", metric.getSubcategory().getSortPriority());
      sb.appendNonNull("subcategoryDesignCode", metric.getSubcategory().getDesignCode());
    }
    sb.appendNonNull("units", metric.getUnits());
    sb.append("thresholdType", metric.getThresholdType());
    sb.appendNonNull("minimum", metric.getMinimum());
    sb.appendNonNull("maximum", metric.getMaximum());
    sb.appendNonNull("sortPriority", metric.getSortPriority());
    sb.appendNonNull("nucleicAcidType", metric.getNucleicAcidType());
    sb.appendNonNull("tissueMaterial", metric.getTissueMaterial());
    sb.appendNonNull("tissueType", metric.getTissueType());
    sb.appendNonNull("negateTissueType", metric.getNegateTissueType());
    sb.appendNonNull("tissueOrigin", metric.getTissueOrigin());
    sb.appendNonNull("containerModel", metric.getContainerModel());
    sb.appendNonNull("readLength", metric.getReadLength());
    sb.appendNonNull("readLength2", metric.getReadLength2());
    return sb.toString();
  }
}
