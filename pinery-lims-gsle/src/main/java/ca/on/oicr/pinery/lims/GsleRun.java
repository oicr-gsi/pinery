package ca.on.oicr.pinery.lims;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleRun extends DefaultRun {

  private static final Logger log = LoggerFactory.getLogger(GsleRun.class);

  public void setIdString(String idString) {
    if (idString != null) {
      setId(Integer.parseInt(idString));
    }
  }

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

  public void setCreatedDateString(String createdDateString) {
    setCreatedDate(getDate(createdDateString));
  }

  public void setCreatedByIdString(String createdByIdString) {
    if (createdByIdString != null && !createdByIdString.equals("")) {
      setCreatedById(Integer.parseInt(createdByIdString));
    }
  }

  public void setInstrumentIdString(String instrumentIdString) {
    if (instrumentIdString != null && !instrumentIdString.equals("")) {
      setInstrumentId((Integer.parseInt(instrumentIdString)));
    }
  }

  public void setModifiedByIdString(String modifiedByIdString) {
    if (modifiedByIdString != null && !modifiedByIdString.equals("")) {
      setModifiedById(Integer.parseInt(modifiedByIdString));
    }
  }

  public void setModifiedDateString(String modifiedDateString) {
    setModified(getDate(modifiedDateString));
  }

  public void setCompletionDateString(String completionDateString) {
    setCompletionDate(getDate(completionDateString));
  }

  private Date getDate(String dateString) {
    if (dateString != null && !dateString.equals("")) {
      try {
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return dateTime.toDate();
      } catch (IllegalArgumentException e) {
        log.error("Error converting [{}] date format. {}", dateString, e);
      }
    }
    return null;
  }
}
