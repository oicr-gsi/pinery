package ca.on.oicr.pinery.lims.gsle;

import ca.on.oicr.pinery.lims.Workset;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemporaryWorkset extends Workset {

  private static final Logger log = LoggerFactory.getLogger(TemporaryWorkset.class);

  // "2012-06-12 14:47:09-04"
  public static final DateTimeFormatter dateTimeFormatter =
      new DateTimeFormatterBuilder()
          .appendYear(4, 4)
          .appendLiteral('-')
          .appendMonthOfYear(2)
          .appendLiteral('-')
          .appendDayOfMonth(2)
          .appendLiteral(' ')
          .appendHourOfDay(2)
          .appendLiteral(':')
          .appendMinuteOfHour(2)
          .appendLiteral(':')
          .appendSecondOfMinute(2)
          .appendTimeZoneOffset(null, true, 1, 1)
          .toFormatter();

  private String sampleId;

  public String getSampleId() {
    return sampleId;
  }

  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  public void setCreatedByIdString(String createdByIdString) {
    if (createdByIdString != null) {
      setCreatedById(Integer.parseInt(createdByIdString));
    }
  }

  public void setCreatedString(String created) {
    if (created != null && !created.equals("")) {
      try {
        DateTime dateTime = dateTimeFormatter.parseDateTime(created);
        setCreated(dateTime.toDate());
      } catch (IllegalArgumentException e) {
        log.error("Error converting created [{}] date format. {}", created, e);
      }
    }
  }

  public Workset makeWorkset() {
    Workset ws = new Workset();
    ws.setId(this.getId());
    ws.setName(this.getName());
    ws.setDescription(this.getDescription());
    ws.setBarcode(this.getBarcode());
    ws.setCreated(getCreated());
    ws.setCreatedById(getCreatedById());
    ws.addSampleId(this.getSampleId());
    return ws;
  }
}
