package ca.on.oicr.pinery.lims;

import java.time.ZonedDateTime;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleChange extends DefaultChange {

  private static final Logger log = LoggerFactory.getLogger(GsleChange.class);
  private String sampleId;

  public String getSampleId() {
    return sampleId;
  }

  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  public void setCreatedString(String created) {
    if (created != null && !created.equals("")) {
      try {
        ZonedDateTime dateTime = ZonedDateTime.parse(created, GsleSample.dateTimeFormatter);
        setCreated(Date.from(dateTime.toInstant()));
      } catch (IllegalArgumentException e) {
        log.error("Error converting created [{}] date format. {}", created, e);
      }
    }
  }

  public void setCreatedByIdString(String createdByIdString) {
    if (createdByIdString != null && !createdByIdString.equals("")) {
      setCreatedById(Integer.parseInt(createdByIdString));
    }
  }
}
