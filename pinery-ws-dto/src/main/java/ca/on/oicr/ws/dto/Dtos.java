package ca.on.oicr.ws.dto;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import ca.on.oicr.pinery.api.PreparationKit;
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
		if(from.getArchived() != null) {
			dto.setArchived(from.getArchived());
		}
		if(from.getCreated() != null) {
			dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
		}
		if(from.getModified() != null) {
			dto.setModifiedDate(dateTimeFormatter.print(from.getModified().getTime()));
		}
		if(from.getTubeBarcode() != null) {
			dto.setTubeBarcode(from.getTubeBarcode());
		}
		if(from.getVolume() != null) {
			dto.setVolume(from.getVolume());
		}
		if(from.getConcentration() != null) {
			dto.setConcentration(from.getConcentration());
		}
		if(from.getStorageLocation() != null) {
			dto.setStorageLocation(from.getStorageLocation());
		}
		if(from.getPreparationKit() != null) {
			dto.setPreparationKit(asDto(from.getPreparationKit()));
		}
		if(from.getProject() != null) {
			dto.setProjectName(from.getProject());
		}
		if(from.getSampleType() != null) {
			dto.setSampleType(from.getSampleType());
		}
		return dto;
	}
	
	public static PreparationKitDto asDto(PreparationKit from) {
		PreparationKitDto dto = new PreparationKitDto();
		if(from.getName() != null) {
			dto.setName(from.getName());
		}
		if(from.getDescription() != null) {
			dto.setDescription(from.getDescription());
		}
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
