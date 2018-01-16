package ca.on.oicr.pinery.lims;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class Util {

  private Util() {
  }

  static ZonedDateTime optionalDateToZDT(Optional<Date> date) {
    return date.map(Date::toInstant).map(d -> ZonedDateTime.ofInstant(d, ZoneId.of("Z"))).orElse(null);
  }

}
