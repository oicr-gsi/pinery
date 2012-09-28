package ca.on.oicr.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "sample_changelog")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class ChangeLogDto {

	private String sampleUrl;
	private List<ChangeDto> changes;

	@XmlElement(name="sample_url")
	public String getSampleUrl() {
		return sampleUrl;
	}

	public void setSampleUrl(String sampleUrl) {
		this.sampleUrl = sampleUrl;
	}

	public List<ChangeDto> getChanges() {
		return changes;
	}

	public void setChanges(List<ChangeDto> changes) {
		this.changes = changes;
	}

}
