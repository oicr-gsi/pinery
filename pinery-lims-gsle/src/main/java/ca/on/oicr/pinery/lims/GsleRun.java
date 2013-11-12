package ca.on.oicr.pinery.lims;

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
   public static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendYear(4, 4).appendLiteral('-')
         .appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).appendLiteral(' ').appendHourOfDay(2).appendLiteral(':')
         .appendMinuteOfHour(2).appendLiteral(':').appendSecondOfMinute(2).appendTimeZoneOffset(null, true, 1, 1).toFormatter();

   public void setCreatedDateString(String createdDateString) {
      if (createdDateString != null && !createdDateString.equals("")) {
         try {
            DateTime dateTime = dateTimeFormatter.parseDateTime(createdDateString);
            setCreatedDate(dateTime.toDate());
         } catch (IllegalArgumentException e) {
            log.error("Error converting created [{}] date format. {}", createdDateString, e);
         }
      }
   }

   public void setCreatedByIdString(String createdByIdString) {
      if (createdByIdString != null && !createdByIdString.equals("")) {
         setCreatedById(Integer.parseInt(createdByIdString));
      }
   }

}
