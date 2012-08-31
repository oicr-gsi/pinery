package ca.on.oicr.ws.dto;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;

/**
 * Methods to convert between domain objects and dtos.
 * 
 */
public final class Dtos {

	private static DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();

	public static SampleDto asDto(Sample from) {
		SampleDto dto = new SampleDto();
		dto.setId(from.getId());
		dto.setName(from.getName());
		dto.setDescription(from.getDescription());
		return dto;
	}

	public static SampleProjectDto asDto(SampleProject from) {
		SampleProjectDto dto = new SampleProjectDto();
		dto.setName(from.getName());
		dto.setCount(from.getCount());
//		if(from.getArchivedCount() != null) {
//			dto.setArchivedCount(from.getArchivedCount());
//		}
		if (from.getEarliest() != null) {
			dto.setEarliest(dateTimeFormatter.print(from.getEarliest().getTime()));
		}
		if (from.getLatest() != null) {
			dto.setLatest(dateTimeFormatter.print(from.getLatest().getTime()));
		}
		return dto;
	}
}
