package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.lims.LimsLaneAttribute;
import ca.on.oicr.pinery.lims.LimsSampleAttribute;
import ca.on.oicr.pinery.lims.LimsSequencerRunAttribute;
import ca.on.oicr.pinery.lims.SimpleSampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.pinery.ws.util.MapBuilder;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import com.google.common.annotations.VisibleForTesting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Sample Provenance" })
public class SampleProvenanceResource {

  private static final VersionTransformer<SampleProvenance, SampleProvenance> noopTransformer = input -> input;

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v8Transformer = input -> {
    SimpleSampleProvenance modified = SimpleSampleProvenance.from(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.TIMEPOINT.toString());
    modified.setBatchIds(null);
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v7Transformer = input -> {
    SimpleSampleProvenance modified = v8Transformer.transform(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.BARCODE_KIT.toString());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v6Transformer = input -> {
    SimpleSampleProvenance modified = v7Transformer.transform(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.TARGET_CELL_RECOVERY.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.CELL_VIABILITY.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.SPIKE_IN.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.SPIKE_IN_DILUTION.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.SPIKE_IN_VOLUME.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.RUN_PURPOSE.toString());
    modified
        .getSampleAttributes()
        .remove(LimsSampleAttribute.SEQUENCING_CONTROL_TYPE.toString());
    modified.getLaneAttributes().remove(LimsLaneAttribute.RUN_PURPOSE.getKey());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v5Transformer = input -> {
    SimpleSampleProvenance modified = v6Transformer.transform(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.SEX.toString());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v4Transformer = input -> {
    SimpleSampleProvenance modified = v5Transformer.transform(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.DV200.toString());
    modified.getSampleAttributes().remove(LimsSampleAttribute.RIN.toString());
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.CONTAINER_MODEL.getKey());
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.SEQUENCING_KIT.getKey());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v3Transformer = input -> {
    SimpleSampleProvenance modified = v4Transformer.transform(input);
    modified.getSampleAttributes().remove(LimsSampleAttribute.UMIS.toString());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v2Transformer = input -> {
    SimpleSampleProvenance modified = v3Transformer.transform(input);
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.WORKFLOW_TYPE.getKey());
    return modified;
  };

  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v1Transformer = input -> {
    SimpleSampleProvenance modified = v2Transformer.transform(input);
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.RUN_DIRECTORY.getKey());
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.RUN_BASES_MASK.getKey());
    modified
        .getSequencerRunAttributes()
        .remove(LimsSequencerRunAttribute.SEQUENCING_PARAMETERS.getKey());
    modified.getLaneAttributes().remove(LimsLaneAttribute.QC_STATUS.getKey());
    modified.setSkip(false);
    return modified;
  };

  @VisibleForTesting
  protected static final Map<String, VersionTransformer<SampleProvenance, ? extends SampleProvenance>> transformers = new MapBuilder<String, VersionTransformer<SampleProvenance, ? extends SampleProvenance>>() //
      .put("latest", noopTransformer) //
      .put("v9", noopTransformer) //
      .put("v8", v8Transformer) //
      .put("v7", v7Transformer) //
      .put("v6", v6Transformer) //
      .put("v5", v5Transformer) //
      .put("v4", v4Transformer) //
      .put("v3", v3Transformer) //
      .put("v2", v2Transformer) //
      .put("v1", v1Transformer) //
      .build();

  private static final String versions = "latest, v9, v8, v7, v6, v5, v4, v3, v2, v1";

  @Autowired
  private SampleProvenanceService sampleProvenanceService;

  @GetMapping("/provenance/versions")
  @ApiOperation(value = "List available provenance versions", response = String.class, responseContainer = "List")
  public List<String> getProvenanceVersions() {
    return transformers.keySet().stream().sorted().collect(Collectors.toList());
  }

  @GetMapping("/provenance/{version}/sample-provenance")
  @ApiOperation(value = "Get all sample provenance records", response = SampleProvenanceDto.class, responseContainer = "List")
  @ApiResponses({ @ApiResponse(code = 404, message = "Provenance version not found") })
  public List<SampleProvenanceDto> getSamples(
      @ApiParam(allowableValues = versions) @PathVariable String version) {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = transformers.get(version);
    if (transformer == null) {
      throw new RestException(
          HttpStatus.NOT_FOUND, String.format("Provenance version '%s' not found", version));
    }
    List<SampleProvenance> sps = sampleProvenanceService.getSampleProvenance();
    if (sps == null || sps.isEmpty()) {
      return Collections.emptyList();
    }
    return sps.stream().map(transformer::transform).map(Dtos::asDto).collect(Collectors.toList());
  }

  @GetMapping("/sample-provenance")
  @ApiOperation("Get version 1 of all sample provenance records")
  @Deprecated
  public List<SampleProvenanceDto> getSamples() {
    return getSamples("v1");
  }
}
