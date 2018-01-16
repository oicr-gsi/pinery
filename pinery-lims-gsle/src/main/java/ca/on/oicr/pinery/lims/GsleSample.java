package ca.on.oicr.pinery.lims;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleSample extends DefaultSample {

  private static final Logger log = LoggerFactory.getLogger(GsleSample.class);

  public static final String NONE = "NONE";

  // "2012-06-12 14:47:09-04"
  public static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral(' ')
      .appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendOffset("+HH", "Z").toFormatter();

  /**
   * Determines sample project name based on library nomeclature.
   * 
   * @see <a href=
   *      "https://wiki.oicr.on.ca/display/MCPHERSON/LIMS+Guidelines#LIMSGuidelines-LibraryNomencalture">https://wiki.oicr.on.ca/display/MCPHERSON/LIMS+Guidelines#LIMSGuidelines-LibraryNomencalture</a>
   *      Sample names start with a project name 3 to 5 characters long in
   *      uppercase terminated with an underscore.
   */
  @Override
  public String getProject() {
    Pattern pattern = Pattern.compile("^([A-Z0-9]{3,5})_.*$");
    Matcher matcher = pattern.matcher(getName());
    if (matcher.find()) {
      return matcher.group(1);
    }
    return NONE;
  }

  public void setCreatedString(String created) {
    if (created != null && !created.equals("")) {
      try {
        ZonedDateTime dateTime = ZonedDateTime.parse(created, dateTimeFormatter);
        setCreated(Date.from(dateTime.toInstant()));
      } catch (IllegalArgumentException e) {
        log.error("Error converting created [{}] date format. {}", created, e);
      }
    }
  }

  public void setModifiedString(String modified) {
    if (modified != null && !modified.equals("")) {
      try {
        ZonedDateTime dateTime = ZonedDateTime.parse(modified, dateTimeFormatter);
        setModified(Date.from(dateTime.toInstant()));
      } catch (IllegalArgumentException e) {
        log.error("Error converting modified [{}] date format. {}", modified, e);
      }
    }
  }

  public void setArchivedString(String archivedString) {
    if (archivedString != null && archivedString.equals("1")) {
      setArchived(Boolean.TRUE);
    }
    setArchived(Boolean.FALSE);
  }

  public void setPrepKitName(String name) {
    if (name != null && !name.equals(""))
      getOrCreatePreparationKit().setName(name);
  }

  public void setPrepKitDescription(String description) {
    if (description != null && !description.equals(""))
      getOrCreatePreparationKit().setDescription(description);
  }

  public void setStatusString(String status) {
    if (status != null && !status.equals("")) {
      getOrCreateStatus().setName(status);
    }
  }

  public void setStateString(String state) {
    if (state != null && !state.equals("")) {
      getOrCreateStatus().setState(state);
    }
  }

  public void setVolumeString(String volumeString) {
    if (volumeString != null && !volumeString.equals("")) {
      try {
        setVolume(Float.parseFloat(volumeString));
      } catch (NumberFormatException e) {
        log.error("The volume [{}] is not a valid float value. {}", volumeString, e);
      }
    }
  }

  public void setConcentrationString(String concentrationString) {
    if (concentrationString != null && !concentrationString.equals("")) {
      try {
        setConcentration(Float.parseFloat(concentrationString));
      } catch (NumberFormatException e) {
        log.error("The concentration [{}] is not a valid float value. {}", concentrationString, e);
      }
    }
  }

  public void setCreatedByIdString(String createdByIdString) {
    if (createdByIdString != null && !createdByIdString.equals("")) {
      setCreatedById(Integer.parseInt(createdByIdString));
    }
  }

  public void setModifiedByIdString(String modifiedByIdString) {
    if (modifiedByIdString != null && !modifiedByIdString.equals("")) {
      try {
        setModifiedById(Integer.parseInt(modifiedByIdString));
      } catch (NumberFormatException e) {
        log.error("The modified by id [{}] is not a valid integer value. {}", modifiedByIdString, e);
      }
    }
  }

}
