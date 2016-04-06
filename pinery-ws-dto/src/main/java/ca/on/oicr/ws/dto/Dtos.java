package ca.on.oicr.ws.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Status;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import ca.on.oicr.gsi.provenance.api.model.SampleProvenance;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.beanutils.BeanUtils;

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
      if (!StringUtils.isBlank(from.getDescription())) {
         dto.setDescription(from.getDescription());
      }
      if (from.getArchived() != null) {
         dto.setArchived(from.getArchived());
      }
      if (from.getCreated() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
      }
      if (from.getCreatedById() != null) {
         dto.setCreatedById(from.getCreatedById());
      }
      if (from.getModified() != null) {
         dto.setModifiedDate(dateTimeFormatter.print(from.getModified().getTime()));
      }
      if (from.getModifiedById() != null) {
         dto.setModifiedById(from.getModifiedById());
      }
      if (from.getTubeBarcode() != null) {
         dto.setTubeBarcode(from.getTubeBarcode());
      }
      if (from.getVolume() != null) {
         dto.setVolume(from.getVolume());
      }
      if (from.getConcentration() != null) {
         dto.setConcentration(from.getConcentration());
      }
      if (from.getStorageLocation() != null) {
         dto.setStorageLocation(from.getStorageLocation());
      }
      if (from.getPreparationKit() != null) {
         dto.setPreparationKit(asDto(from.getPreparationKit()));
      }
      if (from.getStatus() != null) {
         dto.setStatus(asDto(from.getStatus()));
      }
      if (from.getProject() != null) {
         dto.setProjectName(from.getProject());
      }
      if (from.getSampleType() != null) {
         dto.setSampleType(from.getSampleType());
      }
      if (from.getAttributes() != null && !from.getAttributes().isEmpty()) {
         dto.setAttributes(asDto(from.getAttributes()));
      }
      if (from.getSampleType() != null && !from.getSampleType().equals("")) {
         dto.setSampleType(from.getSampleType());
      }
      if (from.getParents() != null) {
        Set<SampleReferenceDto> parents = new HashSet<>();
        for (Integer parentId : from.getParents()) {
          parents.add(new SampleReferenceDto(parentId));
        }
        dto.setParents(parents);
      }
      if (from.getChildren() != null) {
        Set<SampleReferenceDto> children = new HashSet<>();
        for (Integer childId : from.getChildren()) {
          children.add(new SampleReferenceDto(childId));
        }
        dto.setChildren(children);
      }
      return dto;
   }

   public static Set<AttributeDto> asDto(Set<Attribute> from) {
      Set<AttributeDto> dtoSet = Sets.newHashSet();
      for (Attribute attribute : from) {
         if (!StringUtils.isBlank(attribute.getValue())) {
            dtoSet.add(asDto(attribute));
         }
      }
      return dtoSet;
   }

   public static AttributeDto asDto(Attribute from) {
      AttributeDto dto = new AttributeDto();

      if (from.getValue() != null) {
         dto.setValue(from.getValue());
      }

      if (from.getName() != null) {
         dto.setName(from.getName());
      }

      return dto;
   }

   public static PreparationKitDto asDto(PreparationKit from) {
      PreparationKitDto dto = new PreparationKitDto();
      if (from.getName() != null) {
         dto.setName(from.getName());
      }
      if (from.getDescription() != null) {
         dto.setDescription(from.getDescription());
      }
      return dto;
   }

   public static StatusDto asDto(Status from) {
      StatusDto dto = new StatusDto();
      if (from.getName() != null) {
         dto.setName(from.getName());
      }
      if (from.getState() != null) {
         dto.setState(from.getState());
      }
      return dto;
   }

   public static SampleProjectDto asDto(SampleProject from) {
      SampleProjectDto dto = new SampleProjectDto();
      dto.setName(from.getName());
      dto.setCount(from.getCount());
      if (from.getEarliest() != null) {
         dto.setEarliest(dateTimeFormatter.print(from.getEarliest().getTime()));
      }
      if (from.getLatest() != null) {
         dto.setLatest(dateTimeFormatter.print(from.getLatest().getTime()));
      }
      return dto;
   }

   public static TypeDto asDto(Type from) {
      TypeDto dto = new TypeDto();
      dto.setName(from.getName());
      dto.setCount(from.getCount());
      if (from.getEarliest() != null) {
         dto.setEarliest(dateTimeFormatter.print(from.getEarliest().getTime()));
      }
      if (from.getLatest() != null) {
         dto.setLatest(dateTimeFormatter.print(from.getLatest().getTime()));
      }
      return dto;
   }

   public static AttributeNameDto asDto(AttributeName from) {
      AttributeNameDto dto = new AttributeNameDto();
      dto.setName(from.getName());
      dto.setCount(from.getCount());
      if (from.getEarliest() != null) {
         dto.setEarliest(dateTimeFormatter.print(from.getEarliest().getTime()));
      }
      if (from.getLatest() != null) {
         dto.setLatest(dateTimeFormatter.print(from.getLatest().getTime()));
      }
      return dto;
   }

   public static ChangeDto asDto(Change from) {
      ChangeDto dto = new ChangeDto();
      if (!StringUtils.isBlank(from.getAction())) {
         dto.setAction(from.getAction());
      }
      if (!StringUtils.isBlank(from.getComment())) {
         dto.setComment(from.getComment());
      }
      if (from.getCreated() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
      }
      if (from.getCreatedById() != null) {
         dto.setCreatedById(from.getCreatedById());
      }
      return dto;
   }

   public static ChangeLogDto asDto(ChangeLog from) {
      ChangeLogDto dto = new ChangeLogDto();
      if (from.getSampleId() != null) {
        dto.setSampleId(from.getSampleId());
      }
      if (!from.getChanges().isEmpty()) {
         List<ChangeDto> changes = Lists.newArrayList();
         for (Change change : from.getChanges()) {
            changes.add(asDto(change));
         }
         dto.setChanges(changes);
      }
      return dto;
   }

   public static OrderDto asDto(Order from) {

      OrderDto dto = new OrderDto();
      dto.setId(from.getId());

      if (!StringUtils.isBlank(from.getProject())) {
         dto.setProject(from.getProject());
      }
      if (!StringUtils.isBlank(from.getStatus())) {
         dto.setStatus(from.getStatus());
      }
      if (!StringUtils.isBlank(from.getPlatform())) {
         dto.setPlatform(from.getPlatform());
      }
      if (from.getCreatedDate() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreatedDate().getTime()));
      }
      if (from.getCreatedById() != null) {
        dto.setCreatedById(from.getCreatedById());
      }
      if (from.getModifiedDate() != null) {
         dto.setModifiedDate(dateTimeFormatter.print(from.getModifiedDate().getTime()));
      }
      if (from.getModifiedById() != null) {
        dto.setModifiedById(from.getModifiedById());
      }
      if (from.getSamples() != null && !from.getSamples().isEmpty()) {
         dto.setSamples(asDto1(from.getSamples()));
      }
      return dto;
   }

   public static Set<OrderDtoSample> asDto1(Set<OrderSample> from) {

      Set<OrderDtoSample> dtoSet = Sets.newHashSet();
      for (OrderSample orderSample : from) {
         dtoSet.add(asDto(orderSample));
      }
      return dtoSet;
   }

   public static OrderDtoSample asDto(OrderSample from) {
      OrderDtoSample dto = new OrderDtoSample();
      dto.setId(from.getId());
      if (from.getAttributes() != null && !from.getAttributes().isEmpty()) {
         dto.setAttributes(asDto(from.getAttributes()));
      }
      if (!StringUtils.isBlank(from.getBarcode())) {
         dto.setBarcode(from.getBarcode());
      }
      if (!StringUtils.isBlank(from.getBarcodeTwo())) {
         dto.setBarcodeTwo(from.getBarcodeTwo());
      }
      if (!StringUtils.isBlank(from.getUrl())) {
         dto.setUrl(from.getUrl());
      }
      return dto;
   }

   public static RunDto asDto(Run from) {

      RunDto dto = new RunDto();
      dto.setId(from.getId());

      if (!StringUtils.isBlank(from.getState())) {
         dto.setState(from.getState());
      }
      if (!StringUtils.isBlank(from.getName())) {
         dto.setName(from.getName());
      }
      if (!StringUtils.isBlank(from.getBarcode())) {
         dto.setBarcode(from.getBarcode());
      }
      if (from.getCreatedDate() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreatedDate().getTime()));
      }
      if (from.getSamples() != null && !from.getSamples().isEmpty()) {
         dto.setPositions(asDto2(from.getSamples()));
      }
      if (from.getCreatedById() != null) {
        dto.setCreatedById(from.getCreatedById());
      }
      if (from.getInstrumentId() != null) {
        dto.setInstrumentId(from.getInstrumentId());
      }
      if (!StringUtils.isBlank(from.getInstrumentName())) {
        dto.setInstrumentName(from.getInstrumentName());
      }
      return dto;
   }

   public static Set<RunDtoPosition> asDto2(Set<RunPosition> from) {

      Set<RunDtoPosition> dtoSet = Sets.newHashSet();
      for (RunPosition runSample : from) {
         dtoSet.add(asDto(runSample));
      }
      return dtoSet;
   }

   public static RunDtoPosition asDto(RunPosition from) {
      RunDtoPosition dto = new RunDtoPosition();

      if (from.getPosition() != null) {
         dto.setPosition(from.getPosition());
      }
      if (from.getRunSample() != null && !from.getRunSample().isEmpty()) {
         dto.setSamples(asDto3(from.getRunSample()));
      }
      return dto;
   }

   public static Set<RunDtoSample> asDto3(Set<RunSample> from) {
      Set<RunDtoSample> dtoSet = Sets.newHashSet();
      for (RunSample runSample : from) {
         dtoSet.add(asDto3(runSample));
      }
      return dtoSet;
   }

   public static RunDtoSample asDto3(RunSample from) {
      RunDtoSample dto = new RunDtoSample();
      dto.setId(from.getId());
      if (!StringUtils.isBlank(from.getBarcode())) {
         dto.setBarcode(from.getBarcode());
      }
      if (!StringUtils.isBlank(from.getBarcodeTwo())) {
         dto.setBarcodeTwo(from.getBarcodeTwo());
      }
      if (!StringUtils.isBlank(from.getUrl())) {
         dto.setUrl(from.getUrl());
      }
      return dto;
   }

   public static UserDto asDto(User from) {
      UserDto dto = new UserDto();
      dto.setId(from.getId());

      if (from.getArchived() != null) {
         dto.setArchived(from.getArchived());
      }
      if (from.getCreated() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
      }
      if (from.getModified() != null) {
         dto.setModifiedDate(dateTimeFormatter.print(from.getModified().getTime()));
      }
      if (!StringUtils.isBlank(from.getTitle())) {
         dto.setTitle(from.getTitle());
      }
      if (!StringUtils.isBlank(from.getFirstname())) {
         dto.setFirstname(from.getFirstname());
      }
      if (!StringUtils.isBlank(from.getLastname())) {
         dto.setLastname(from.getLastname());
      }
      if (!StringUtils.isBlank(from.getEmail())) {
         dto.setEmail(from.getEmail());
      }
      if (!StringUtils.isBlank(from.getPhone())) {
         dto.setPhone(from.getPhone());
      }
      if (!StringUtils.isBlank(from.getComment())) {
         dto.setComment(from.getComment());
      }
      if (!StringUtils.isBlank(from.getInstitution())) {
         dto.setInstitution(from.getInstitution());
      }
      if (from.getCreatedById() != null) {
        dto.setCreatedById(from.getCreatedById());
      }
      if (from.getModifiedById() != null) {
        dto.setModifiedById(from.getModifiedById());
      }
      return dto;
   }

   public static InstrumentModelDto asDto(InstrumentModel from) {
      InstrumentModelDto dto = new InstrumentModelDto();
      dto.setId(from.getId());
      if (!StringUtils.isBlank(from.getName())) {
         dto.setName(from.getName());
      }
      if (from.getCreated() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
      }
      if (from.getCreatedById() != null) {
        dto.setCreatedById(from.getCreatedById());
      }
      if (from.getModified() != null) {
         dto.setModifiedDate(dateTimeFormatter.print(from.getModified().getTime()));
      }
      if (from.getModifiedById() != null) {
        dto.setModifiedById(from.getModifiedById());
      }
      return dto;
   }

   public static InstrumentDto asDto(Instrument from) {
      InstrumentDto dto = new InstrumentDto();
      dto.setId(from.getId());
      if (!StringUtils.isBlank(from.getName())) {
         dto.setName(from.getName());
      }
      if (from.getCreated() != null) {
         dto.setCreatedDate(dateTimeFormatter.print(from.getCreated().getTime()));
      }

      if (from.getModelId() != null) {
         dto.setModelId(from.getModelId());
      }
      return dto;
   }
   
    public static SampleProvenanceDto asDto(SampleProvenance from) {
        SampleProvenanceDto dto = new SampleProvenanceDto();
        try {
            BeanUtils.copyProperties(dto, from);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }
    
    public static List<SampleProvenanceDto> asDto(Collection<SampleProvenance> from) {
        List<SampleProvenanceDto> result = new ArrayList<>();
        for (SampleProvenance sp : from) {
            result.add(asDto(sp));
        }
        return result;
    }
    
}
