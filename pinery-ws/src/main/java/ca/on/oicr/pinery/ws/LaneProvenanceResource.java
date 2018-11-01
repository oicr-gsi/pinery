package ca.on.oicr.pinery.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.annotations.VisibleForTesting;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.pinery.ws.util.MapBuilder;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.LaneProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 * @author mlaszloffy
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = { "Lane Provenance" })
public class LaneProvenanceResource {

  private static final VersionTransformer<LaneProvenance> noopTransformer = input -> input;

  @VisibleForTesting
  protected static final Map<String, VersionTransformer<LaneProvenance>> transformers //
      = new MapBuilder<String, VersionTransformer<LaneProvenance>>() //
          .put("latest", noopTransformer) //
          .put("v1", noopTransformer) //
          .build();

  @Autowired
  private LaneProvenanceService laneProvenanceService;

  @GetMapping(path = "/provenance/{version}/lane-provenance")
  @ApiOperation(value = "Get all lane provenance records", response = LaneProvenanceDto.class, responseContainer = "List")
  @ApiResponses({@ApiResponse(code = 404, message = "Provenance version not found")})
  public List<LaneProvenanceDto> getLanes(@PathVariable String version) {
    VersionTransformer<LaneProvenance> transformer = transformers.get(version);
    if (transformer == null) {
      throw new RestException(HttpStatus.NOT_FOUND, String.format("Provenance version '%s' not found", version));
    }
    List<LaneProvenance> lps = laneProvenanceService.getLaneProvenance();
    if (lps == null || lps.isEmpty()) {
      return Collections.emptyList();
    }
    return lps.stream().map(transformer::transform).map(Dtos::asDto).collect(Collectors.toList());
  }
  
  @GetMapping("/lane-provenance")
  @ApiOperation("Redirect to versioned lane provenance URL")
  @ApiResponses({@ApiResponse(code = 301, message = "Permanent redirect")})
  @Deprecated
  public void redirectOldUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
    response.setHeader("Location", request.getContextPath() + "/provenance/latest/lane-provenance");
  }
  
}
