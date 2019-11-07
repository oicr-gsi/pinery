package ca.on.oicr.pinery.lims;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleInstrument extends DefaultInstrument {

  private static final Logger log = LoggerFactory.getLogger(GsleInstrument.class);

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

  public void setIdString(String idString) {
    if (idString != null) {
      setId(Integer.parseInt(idString));
    }
  }
}
