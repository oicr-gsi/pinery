package ca.on.oicr.pinery.lims;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleOrder extends DefaultOrder {

   private static final Logger log = LoggerFactory.getLogger(GsleOrder.class);

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

   public void setModifiedDateString(String modifiedDateString) {
      if (modifiedDateString != null && !modifiedDateString.equals("")) {
         try {
            DateTime dateTime = dateTimeFormatter.parseDateTime(modifiedDateString);
            setModifiedDate(dateTime.toDate());
         } catch (IllegalArgumentException e) {
            log.error("Error converting modified [{}] date format. {}", modifiedDateString, e);
         }
      }
   }

   public void setIdString(String idString) {
      if (idString != null) {
         setId(Integer.parseInt(idString));
      }
   }

   public void setCreatedByIdString(String createdByIdString) {

      if (createdByIdString != null && !createdByIdString.equals("")) {
         setCreatedById(Integer.parseInt(createdByIdString));
      }
   }

   public void setModifiedByIdString(String modifiedByIdString) {

      if (modifiedByIdString != null && !modifiedByIdString.equals("")) {
         setModifiedById(Integer.parseInt(modifiedByIdString));
      }
   }

}