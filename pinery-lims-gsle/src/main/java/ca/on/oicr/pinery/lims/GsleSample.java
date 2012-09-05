package ca.on.oicr.pinery.lims;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class GsleSample extends DefaultSample {

//	Logger logger = LoggerFactory.getLogger(GsleSample.class);
	
	private String idString;
	private String createdString;
	private String modifiedString;
	private String archivedString;

	public static final String NONE = "NONE";

	// "2012-06-12 14:47:09-04"
	private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendYear(4, 4)
			.appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).appendLiteral(' ')
			.appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2).appendLiteral(':').appendSecondOfMinute(2)
			.appendTimeZoneOffset(null, true, 1, 1).toFormatter();

	public String getProject() {
		Pattern pattern = Pattern.compile("^([A-Z]{3,4})_.*$");
		Matcher matcher = pattern.matcher(getName());
		if (matcher.find()) {
			return matcher.group(1);
		}
		return NONE;
	}

	public void setCreatedString(String created) {
		if (created != null && !created.equals("")) {
			try {
				DateTime dateTime = dateTimeFormatter.parseDateTime(created);
				setCreated(dateTime.toDate());
			} catch (IllegalArgumentException e) {
//				logger.error("Error converting created [{}] date format. {}", created, e );
			}
		}
	}

	public void setModifiedString(String modified) {
		if (modified != null && !modified.equals("")) {
			try {
			DateTime dateTime = dateTimeFormatter.parseDateTime(modified);
			setModified(dateTime.toDate());
			} catch(IllegalArgumentException e) {
//				logger.error("Error converting modified [{}] date format. {}", modified, e );
			}
		}
	}

	public void setIdString(String idString) {
		if (idString != null) {
			setId(Integer.parseInt(idString));
		}
	}

	public void setArchivedString(String archivedString) {
		if (archivedString != null && archivedString.equals("1")) {
			setArchived(Boolean.TRUE);
		}
		setArchived(Boolean.FALSE);
	}
}
