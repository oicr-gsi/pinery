package ca.on.oicr.pinery.lims;

import org.joda.time.DateTime;
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
				DateTime dateTime = GsleSample.dateTimeFormatter.parseDateTime(created);
				setCreated(dateTime.toDate());
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
