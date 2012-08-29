package ca.on.oicr.ws.dto;

import ca.on.oicr.pinery.api.Sample;

/**
 * Methods to convert between domain objects and dtos.
 * 
 */
public final class Dtos {


	public static SampleDto asDto(Sample from) {
		SampleDto dto = new SampleDto();
		dto.setId(from.getId());
		dto.setName(from.getName());
		dto.setDescription(from.getDescription());
		return dto;
	}

//	public static AttributeDto asDto(Attribute from) {
//		AttributeDto dto = new AttributeDto();
//		dto.setName(from.getTag());
//		dto.setValue(from.getValue());
//		dto.setUnit(from.getUnit());
//		return dto;
//	}
//
//
//}
//
//	public static FileDto asDto(File file) {
//		FileDto dto = new FileDto();
//		dto.setFilePath(file.getFilePath());
//		if (file.getMetaType() != null) {
//			dto.setMetaType(file.getMetaType());
//		}
//		if (file.getDescription() != null) {
//			dto.setDescription(file.getDescription());
//		}
//		if (file.getMd5sum() != null) {
//			dto.setMd5sum(file.getMd5sum());
//		}
//		if (file.getSize() != null) {
//			dto.setSize(file.getSize());
//		}
//		if (file.getType() != null) {
//			dto.setType(file.getType());
//		}
//		return dto;
//	}
}
