package ca.on.oicr.pinery.ws;

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

import com.google.common.annotations.VisibleForTesting;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.lims.LimsLaneAttribute;
import ca.on.oicr.pinery.lims.LimsSequencerRunAttribute;
import ca.on.oicr.pinery.lims.SimpleSampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.pinery.ws.util.MapBuilder;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = { "Sample Provenance" })
public class SampleProvenanceResource {

  private static final VersionTransformer<SampleProvenance, SampleProvenance> noopTransformer = input -> input;
  
  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v2Transformer = input -> {
    SimpleSampleProvenance modified = SimpleSampleProvenance.from(input);
    modified.getSequencerRunAttributes().remove(LimsSequencerRunAttribute.WORKFLOW_TYPE.getKey());
    return modified;
  };
  
  private static final VersionTransformer<SampleProvenance, SimpleSampleProvenance> v1Transformer = input -> {
    SimpleSampleProvenance modified = v2Transformer.transform(input);
    modified.getSequencerRunAttributes().remove(LimsSequencerRunAttribute.RUN_DIRECTORY.getKey());
    modified.getSequencerRunAttributes().remove(LimsSequencerRunAttribute.RUN_BASES_MASK.getKey());
    modified.getSequencerRunAttributes().remove(LimsSequencerRunAttribute.SEQUENCING_PARAMETERS.getKey());
    modified.getLaneAttributes().remove(LimsLaneAttribute.QC_STATUS.getKey());
    modified.setSkip(false);
    return modified;
  };

  @VisibleForTesting
  protected static final Map<String, VersionTransformer<SampleProvenance, ? extends SampleProvenance>> transformers //
      = new MapBuilder<String, VersionTransformer<SampleProvenance, ? extends SampleProvenance>>() //
          .put("latest", noopTransformer) //
          .put("v3", noopTransformer) //
          .put("v2", v2Transformer) //
          .put("v1", v1Transformer) //
          .build();
  
  private static final String versions = "latest, v3, v2, v1";

  @Autowired
  private SampleProvenanceService sampleProvenanceService;

  @GetMapping("/provenance/{version}/sample-provenance")
  @ApiOperation(value = "Get all sample provenance records", response = SampleProvenanceDto.class, responseContainer = "List")
  @ApiResponses({@ApiResponse(code = 404, message = "Provenance version not found")})
  public List<SampleProvenanceDto> getSamples(@ApiParam(allowableValues = versions) @PathVariable String version) {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = transformers.get(version);
    if (transformer == null) {
      throw new RestException(HttpStatus.NOT_FOUND, String.format("Provenance version '%s' not found", version));
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
