package ca.on.oicr.pinery.lims;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class Util {

  private Util() {}

  static ZonedDateTime optionalDateToZDT(Optional<Date> date) {
    return date.map(d -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.of("Z")))
        .orElse(null);
  }
}
