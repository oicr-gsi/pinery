package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Methods to convert between domain objects and dtos. */
public final class Dtos {

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

  public static SampleDto asDto(Sample from) {
    SampleDto dto = new SampleDto();

    dto.setId(from.getId());

    dto.setName(from.getName());
    if (!isBlank(from.getDescription())) {
      dto.setDescription(from.getDescription());
    }
    if (from.getArchived() != null) {
      dto.setArchived(from.getArchived());
    }
    if (from.getCreated() != null) {
      dto.setCreatedDate(format(from.getCreated()));
    }
    if (from.getCreatedById() != null) {
      dto.setCreatedById(from.getCreatedById());
    }
    if (from.getModified() != null) {
      dto.setModifiedDate(format(from.getModified()));
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
    dto.setConcentrationUnits(from.getConcentrationUnits());
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
      for (String parentId : from.getParents()) {
        parents.add(new SampleReferenceDto(parentId));
      }
      dto.setParents(parents);
    }
    if (from.getChildren() != null) {
      Set<SampleReferenceDto> children = new HashSet<>();
      for (String childId : from.getChildren()) {
        children.add(new SampleReferenceDto(childId));
      }
      dto.setChildren(children);
    }
    dto.setPreMigrationId(from.getPreMigrationId());
    return dto;
  }

  public static Set<AttributeDto> asDto(Set<Attribute> from) {
    Set<AttributeDto> dtoSet = Sets.newHashSet();
    for (Attribute attribute : from) {
      dtoSet.add(asDto(attribute));
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
    dto.setName(from.getName());
    dto.setState(from.getState());
    dto.setNote(from.getNote());
    dto.setDate(format(from.getDate()));
    dto.setUserId(from.getUserId());
    return dto;
  }

  public static SampleProjectDto asDto(SampleProject from) {
    SampleProjectDto dto = new SampleProjectDto();
    dto.setName(from.getName());
    dto.setCount(from.getCount());
    dto.setArchivedCount(from.getArchivedCount());
    dto.setActive(from.isActive());
    dto.setPipeline(from.getPipeline());
    dto.setSecondaryNamingSCheme(from.isSecondaryNamingScheme());
    dto.setEarliest(format(from.getEarliest()));
    dto.setLatest(format(from.getLatest()));
    dto.setCreatedDate(format(from.getCreated()));
    dto.setRebNumber(from.getRebNumber());
    dto.setRebExpiry(format(from.getRebExpiry()));
    dto.setDescription(from.getDescription());
    dto.setSamplesExpected(from.getSamplesExpected());
    dto.setContactName(from.getContactName());
    dto.setContactEmail(from.getContactEmail());
    if (from.getDeliverables() != null) {
      dto.setDeliverables(from.getDeliverables().stream().collect(Collectors.toSet()));
    }
    dto.setAnalysisReviewRequired(from.getAnalysisReviewRequired());
    return dto;
  }

  public static TypeDto asDto(Type from) {
    TypeDto dto = new TypeDto();
    dto.setName(from.getName());
    dto.setCount(from.getCount());
    if (from.getEarliest() != null) {
      dto.setEarliest(format(from.getEarliest()));
    }
    if (from.getLatest() != null) {
      dto.setLatest(format(from.getLatest()));
    }
    dto.setArchivedCount(from.getArchivedCount());
    return dto;
  }

  public static AttributeNameDto asDto(AttributeName from) {
    AttributeNameDto dto = new AttributeNameDto();
    dto.setName(from.getName());
    dto.setCount(from.getCount());
    dto.setArchivedCount(from.getArchivedCount());
    if (from.getEarliest() != null) {
      dto.setEarliest(format(from.getEarliest()));
    }
    if (from.getLatest() != null) {
      dto.setLatest(format(from.getLatest()));
    }
    return dto;
  }

  public static ChangeDto asDto(Change from) {
    ChangeDto dto = new ChangeDto();
    if (!isBlank(from.getAction())) {
      dto.setAction(from.getAction());
    }
    if (!isBlank(from.getComment())) {
      dto.setComment(from.getComment());
    }
    if (from.getCreated() != null) {
      dto.setCreatedDate(format(from.getCreated()));
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

    if (!isBlank(from.getProject())) {
      dto.setProject(from.getProject());
    }
    if (!isBlank(from.getStatus())) {
      dto.setStatus(from.getStatus());
    }
    if (!isBlank(from.getPlatform())) {
      dto.setPlatform(from.getPlatform());
    }
    if (from.getCreatedDate() != null) {
      dto.setCreatedDate(format(from.getCreatedDate()));
    }
    if (from.getCreatedById() != null) {
      dto.setCreatedById(from.getCreatedById());
    }
    if (from.getModifiedDate() != null) {
      dto.setModifiedDate(format(from.getModifiedDate()));
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
    if (!isBlank(from.getBarcode())) {
      dto.setBarcode(from.getBarcode());
    }
    if (!isBlank(from.getBarcodeTwo())) {
      dto.setBarcodeTwo(from.getBarcodeTwo());
    }
    if (!isBlank(from.getUrl())) {
      dto.setUrl(from.getUrl());
    }
    return dto;
  }

  public static RunDto asDto(Run from) {

    RunDto dto = new RunDto();
    dto.setId(from.getId());

    if (!isBlank(from.getState())) {
      dto.setState(from.getState());
    }
    if (!isBlank(from.getName())) {
      dto.setName(from.getName());
    }
    if (!isBlank(from.getBarcode())) {
      dto.setBarcode(from.getBarcode());
    }
    if (from.getCreatedDate() != null) {
      dto.setCreatedDate(format(from.getCreatedDate()));
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
    if (!isBlank(from.getInstrumentName())) {
      dto.setInstrumentName(from.getInstrumentName());
    }
    dto.setModifiedById(from.getModifiedById());
    if (from.getModified() != null) {
      dto.setModifiedDate(format(from.getModified()));
    }
    if (from.getStartDate() != null) {
      dto.setStartDate(format(from.getStartDate()));
    }
    if (from.getCompletionDate() != null) {
      dto.setCompletionDate(format(from.getCompletionDate()));
    }
    dto.setReadLength(from.getReadLength());
    dto.setRunDirectory(from.getRunDirectory());
    dto.setRunBasesMask(from.getRunBasesMask());
    dto.setSequencingParameters(from.getSequencingParameters());
    dto.setChemistry(from.getChemistry());
    dto.setWorkflowType(from.getWorkflowType());
    dto.setContainerModel(from.getContainerModel());
    dto.setSequencingKit(from.getSequencingKit());
    if (from.getStatus() != null) {
      dto.setStatus(asDto(from.getStatus()));
    }
    if (from.getDataReview() == null) {
      dto.setDataReview("Pending");
    } else {
      dto.setDataReview(from.getDataReview() ? "Passed" : "Failed");
    }
    dto.setDataReviewDate(format(from.getDataReviewDate()));
    dto.setDataReviewerId(from.getDataReviewerId());
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
    dto.setPosition(from.getPosition());
    dto.setPoolId(from.getPoolId());
    dto.setPoolName(from.getPoolName());
    dto.setPoolBarcode(from.getPoolBarcode());
    dto.setPoolDescription(from.getPoolDescription());
    if (from.getPoolStatus() != null) {
      dto.setPoolStatus(asDto(from.getPoolStatus()));
    }
    dto.setPoolCreatedById(from.getPoolCreatedById());
    if (from.getPoolCreated() != null)
      dto.setPoolCreated(format(from.getPoolCreated()));
    dto.setPoolModifiedById(from.getPoolModifiedById());
    if (from.getPoolModified() != null)
      dto.setPoolModified(format(from.getPoolModified()));
    if (from.getRunSample() != null && !from.getRunSample().isEmpty()) {
      dto.setSamples(asDto3(from.getRunSample()));
    }
    dto.setQcStatus(from.getQcStatus());
    dto.setAnalysisSkipped(from.isAnalysisSkipped());
    dto.setRunPurpose(from.getRunPurpose());
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
    if (from.getAttributes() != null && !from.getAttributes().isEmpty()) {
      dto.setAttributes(asDto(from.getAttributes()));
    }
    if (!isBlank(from.getBarcode())) {
      dto.setBarcode(from.getBarcode());
    }
    if (!isBlank(from.getBarcodeTwo())) {
      dto.setBarcodeTwo(from.getBarcodeTwo());
    }
    dto.setRunPurpose(from.getRunPurpose());
    if (!isBlank(from.getUrl())) {
      dto.setUrl(from.getUrl());
    }
    if (from.getStatus() != null) {
      dto.setStatus(asDto(from.getStatus()));
    }
    if (from.getDataReview() == null) {
      dto.setDataReview("Pending");
    } else {
      dto.setDataReview(from.getDataReview() ? "Passed" : "Failed");
    }
    dto.setDataReviewDate(format(from.getDataReviewDate()));
    dto.setDataReviewerId(from.getDataReviewerId());
    return dto;
  }

  public static UserDto asDto(User from) {
    UserDto dto = new UserDto();
    dto.setId(from.getId());

    if (from.getArchived() != null) {
      dto.setArchived(from.getArchived());
    }
    if (from.getCreated() != null) {
      dto.setCreatedDate(format(from.getCreated()));
    }
    if (from.getModified() != null) {
      dto.setModifiedDate(format(from.getModified()));
    }
    if (!isBlank(from.getTitle())) {
      dto.setTitle(from.getTitle());
    }
    if (!isBlank(from.getFirstname())) {
      dto.setFirstname(from.getFirstname());
    }
    if (!isBlank(from.getLastname())) {
      dto.setLastname(from.getLastname());
    }
    if (!isBlank(from.getEmail())) {
      dto.setEmail(from.getEmail());
    }
    if (!isBlank(from.getPhone())) {
      dto.setPhone(from.getPhone());
    }
    if (!isBlank(from.getComment())) {
      dto.setComment(from.getComment());
    }
    if (!isBlank(from.getInstitution())) {
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
    if (!isBlank(from.getName())) {
      dto.setName(from.getName());
    }
    if (from.getCreated() != null) {
      dto.setCreatedDate(format(from.getCreated()));
    }
    if (from.getCreatedById() != null) {
      dto.setCreatedById(from.getCreatedById());
    }
    if (from.getModified() != null) {
      dto.setModifiedDate(format(from.getModified()));
    }
    if (from.getModifiedById() != null) {
      dto.setModifiedById(from.getModifiedById());
    }
    if (from.getPlatform() != null) {
      dto.setPlatform(from.getPlatform());
    }
    return dto;
  }

  public static InstrumentDto asDto(Instrument from) {
    InstrumentDto dto = new InstrumentDto();
    dto.setId(from.getId());
    if (!isBlank(from.getName())) {
      dto.setName(from.getName());
    }
    if (from.getCreated() != null) {
      dto.setCreatedDate(format(from.getCreated()));
    }

    if (from.getModelId() != null) {
      dto.setModelId(from.getModelId());
    }
    return dto;
  }

  public static SampleProvenanceDto asDto(SampleProvenance from) {
    return new SampleProvenanceDto(from);
  }

  public static LaneProvenanceDto asDto(LaneProvenance from) {
    return new LaneProvenanceDto(from);
  }

  public static List<BoxDto> asDtoList(List<Box> fromList) {
    List<BoxDto> toList = Lists.newArrayList();
    for (Box from : fromList) {
      toList.add(asDto(from));
    }
    return toList;
  }

  public static BoxDto asDto(Box from) {
    BoxDto to = new BoxDto();
    to.setId(from.getId());
    to.setName(from.getName());
    to.setDescription(from.getDescription());
    to.setLocation(from.getLocation());
    to.setRows(from.getRows());
    to.setColumns(from.getColumns());
    to.setPositions(asDtoSet(from.getPositions()));
    return to;
  }

  public static Set<BoxPositionDto> asDtoSet(Set<BoxPosition> fromSet) {
    Set<BoxPositionDto> toSet = Sets.newHashSet();
    for (BoxPosition from : fromSet) {
      toSet.add(asDto(from));
    }
    return toSet;
  }

  public static BoxPositionDto asDto(BoxPosition from) {
    BoxPositionDto to = new BoxPositionDto();
    to.setPosition(from.getPosition());
    to.setSampleId(from.getSampleId());
    return to;
  }

  public static AssayTestDto asDto(AssayTest from) {
    AssayTestDto to = new AssayTestDto();
    to.setName(from.getName());
    to.setTissueType(from.getTissueType());
    to.setNegateTissueType(from.getNegateTissueType());
    to.setExtractionSampleType(from.getExtractionSampleType());
    to.setLibrarySourceTemplateType(from.getLibrarySourceTemplateType());
    to.setLibraryQualificationMethod(from.getLibraryQualificationMethod());
    to.setLibraryQualificationSourceTemplateType(from.getLibraryQualificationSourceTemplateType());
    to.setRepeatPerTimepoint(from.getRepeatPerTimepoint());
    to.setPermittedSamples(from.getPermittedSamples());
    return to;
  }

  public static AssayMetricDto asDto(AssayMetric from) {
    AssayMetricDto to = new AssayMetricDto();
    to.setName(from.getName());
    to.setCategory(from.getCategory());
    if (from.getSubcategory() != null) {
      AssayMetricSubcategoryDto toSub = new AssayMetricSubcategoryDto();
      AssayMetricSubcategory fromSub = from.getSubcategory();
      toSub.setName(fromSub.getName());
      toSub.setSortPriority(fromSub.getSortPriority());
      toSub.setDesignCode(fromSub.getDesignCode());
      to.setSubcategory(toSub);
    }
    to.setUnits(from.getUnits());
    to.setThresholdType(from.getThresholdType());
    to.setMinimum(format(from.getMinimum()));
    to.setMaximum(format(from.getMaximum()));
    to.setSortPriority(from.getSortPriority());
    to.setNucleicAcidType(from.getNucleicAcidType());
    to.setTissueMaterial(from.getTissueMaterial());
    to.setTissueType(from.getTissueType());
    if (to.getTissueType() != null) {
      to.setNegateTissueType(from.getNegateTissueType());
    }
    to.setTissueOrigin(from.getTissueOrigin());
    to.setContainerModel(from.getContainerModel());
    to.setReadLength(from.getReadLength());
    to.setReadLength2(from.getReadLength2());
    return to;
  }

  public static AssayTargetsDto asDto(AssayTargets from) {
    AssayTargetsDto to = new AssayTargetsDto();
    to.setCaseDays(from.getCaseDays());
    to.setReceiptDays(from.getReceiptDays());
    to.setExtractionDays(from.getExtractionDays());
    to.setLibraryPreparationDays(from.getLibraryPreparationDays());
    to.setLibraryQualificationDays(from.getLibraryQualificationDays());
    to.setFullDepthSequencingDays(from.getFullDepthSequencingDays());
    to.setAnalysisReviewDays(from.getAnalysisReviewDays());
    to.setReleaseApprovalDays(from.getReleaseApprovalDays());
    to.setReleaseDays(from.getReleaseDays());
    return to;
  }

  public static AssayDto asDto(Assay from) {
    AssayDto to = new AssayDto();
    to.setId(from.getId());
    to.setName(from.getName());
    to.setDescription(from.getDescription());
    to.setVersion(from.getVersion());
    if (from.getTests() != null && !from.getTests().isEmpty()) {
      to.setTests(from.getTests().stream().map(Dtos::asDto).collect(Collectors.toSet()));
    }
    if (from.getMetrics() != null && !from.getMetrics().isEmpty()) {
      to.setMetrics(from.getMetrics().stream().map(Dtos::asDto).collect(Collectors.toSet()));
    }
    if (from.getTargets() != null) {
      to.setTargets(asDto(from.getTargets()));
    }
    return to;
  }

  public static SignOffDto asDto(SignOff from) {
    SignOffDto to = new SignOffDto();
    to.setName(from.getName());
    to.setPassed(from.getPassed());
    to.setDate(format(from.getDate()));
    to.setUserId(from.getUserId());
    return to;
  }

  public static RequisitionPauseDto asDto(RequisitionPause from) {
    RequisitionPauseDto to = new RequisitionPauseDto();
    to.setStartDate(format(from.getStartDate()));
    to.setEndDate(format(from.getEndDate()));
    to.setReason(from.getReason());
    return to;
  }

  public static RequisitionDto asDto(Requisition from) {
    RequisitionDto to = new RequisitionDto();
    to.setId(from.getId());
    to.setName(from.getName());
    if (from.getAssayIds() != null) {
      to.setAssayIds(new HashSet<>(from.getAssayIds()));
    }
    if (from.getSampleIds() != null) {
      to.setSampleIds(new HashSet<>(from.getSampleIds()));
    }
    if (from.getSupplementalSampleIds() != null) {
      to.setSupplementalSampleIds(new HashSet<>(from.getSupplementalSampleIds()));
    }
    if (from.getSignOffs() != null) {
      to.setSignOffs(from.getSignOffs().stream().map(Dtos::asDto).toList());
    }
    if (from.getPauses() != null) {
      to.setPauses(from.getPauses().stream().map(Dtos::asDto).toList());
    }
    to.setStopped(from.isStopped());
    to.setStopReason(from.getStopReason());
    to.setCreatedDate(format(from.getCreated()));
    return to;
  }

  private static boolean isBlank(String str) {
    if (str == null || str.isEmpty()) {
      return true;
    }
    return str.chars().allMatch(Character::isWhitespace);
  }

  private static String format(Date date) {
    if (date == null) {
      return null;
    }
    return format(Instant.ofEpochMilli(date.getTime()));
  }

  private static String format(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.format(dateFormatter);
  }

  private static String format(Instant date) {
    if (date == null) {
      return null;
    }
    return ZonedDateTime.ofInstant(date, ZoneId.systemDefault())
        .truncatedTo(ChronoUnit.SECONDS)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  protected static String format(BigDecimal decimal) {
    if (decimal == null) {
      return null;
    }

    String nice = decimal.toPlainString().replaceFirst("^(.*\\..*?\\d)0+$", "$1");
    if (!nice.contains(".")) {
      nice += ".0";
    }
    return nice;
  }
}
