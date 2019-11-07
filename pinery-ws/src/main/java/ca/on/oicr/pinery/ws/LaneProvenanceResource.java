package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.lims.LimsLaneAttribute;
import ca.on.oicr.pinery.lims.LimsSequencerRunAttribute;
import ca.on.oicr.pinery.lims.SimpleLaneProvenance;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.pinery.ws.util.MapBuilder;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.LaneProvenanceDto;
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

/** @author mlaszloffy */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Lane Provenance"})
public class LaneProvenanceResource {

  private static final VersionTransformer<LaneProvenance, LaneProvenance> noopTransformer =
      input -> input;

  private static final VersionTransformer<LaneProvenance, SimpleLaneProvenance> v3Transformer =
      input -> {
        SimpleLaneProvenance modified = SimpleLaneProvenance.from(input);
        modified
            .getSequencerRunAttributes()
            .remove(LimsSequencerRunAttribute.CONTAINER_MODEL.getKey());
        modified
            .getSequencerRunAttributes()
            .remove(LimsSequencerRunAttribute.SEQUENCING_KIT.getKey());
        return modified;
      };

  private static final VersionTransformer<LaneProvenance, SimpleLaneProvenance> v2Transformer =
      input -> {
        SimpleLaneProvenance modified = v3Transformer.transform(input);
        modified
            .getSequencerRunAttributes()
            .remove(LimsSequencerRunAttribute.WORKFLOW_TYPE.getKey());
        return modified;
      };

  private static final VersionTransformer<LaneProvenance, SimpleLaneProvenance> v1Transformer =
      input -> {
        SimpleLaneProvenance modified = v2Transformer.transform(input);
        modified
            .getSequencerRunAttributes()
            .remove(LimsSequencerRunAttribute.SEQUENCING_PARAMETERS.getKey());
        modified.getLaneAttributes().remove(LimsLaneAttribute.QC_STATUS.getKey());
        modified.setSkip(false);
        return modified;
      };

  @VisibleForTesting
  protected static final Map<String, VersionTransformer<LaneProvenance, ? extends LaneProvenance>>
      transformers //
      =
          new MapBuilder<String, VersionTransformer<LaneProvenance, ? extends LaneProvenance>>() //
              .put("latest", noopTransformer) //
              .put("v5", noopTransformer) //
              .put("v4", v3Transformer) //
              .put("v3", v3Transformer) //
              .put("v2", v2Transformer) //
              .put("v1", v1Transformer) //
              .build();

  private static final String versions = "latest, v5, v4, v3, v2, v1";

  @Autowired private LaneProvenanceService laneProvenanceService;

  @GetMapping(path = "/provenance/{version}/lane-provenance")
  @ApiOperation(
      value = "Get all lane provenance records",
      response = LaneProvenanceDto.class,
      responseContainer = "List")
  @ApiResponses({@ApiResponse(code = 404, message = "Provenance version not found")})
  public List<LaneProvenanceDto> getLanes(
      @ApiParam(allowableValues = versions) @PathVariable String version) {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        transformers.get(version);
    if (transformer == null) {
      throw new RestException(
          HttpStatus.NOT_FOUND, String.format("Provenance version '%s' not found", version));
    }
    List<LaneProvenance> lps = laneProvenanceService.getLaneProvenance();
    if (lps == null || lps.isEmpty()) {
      return Collections.emptyList();
    }
    return lps.stream().map(transformer::transform).map(Dtos::asDto).collect(Collectors.toList());
  }

  @GetMapping("/lane-provenance")
  @ApiOperation("Get version 1 of all lane provenance records")
  @Deprecated
  public List<LaneProvenanceDto> getLanes() {
    return getLanes("v1");
  }
}
