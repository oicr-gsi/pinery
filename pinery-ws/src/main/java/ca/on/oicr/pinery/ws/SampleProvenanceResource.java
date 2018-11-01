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

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.pinery.ws.util.MapBuilder;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = { "Sample Provenance" })
public class SampleProvenanceResource {

  private static final VersionTransformer<SampleProvenance> noopTransformer = input -> input;

  @VisibleForTesting
  protected static final Map<String, VersionTransformer<SampleProvenance>> transformers //
      = new MapBuilder<String, VersionTransformer<SampleProvenance>>() //
          .put("latest", noopTransformer) //
          .put("v1", noopTransformer) //
          .build();

  @Autowired
  private SampleProvenanceService sampleProvenanceService;

  @GetMapping("/provenance/{version}/sample-provenance")
  @ApiOperation(value = "Get all sample provenance records", response = SampleProvenanceDto.class, responseContainer = "List")
  @ApiResponses({@ApiResponse(code = 404, message = "Provenance version not found")})
  public List<SampleProvenanceDto> getSamples(@PathVariable String version) {
    VersionTransformer<SampleProvenance> transformer = transformers.get(version);
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
  @ApiOperation("Redirect to versioned sample provenance URL")
  @Deprecated
  public void redirectOldUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect(request.getContextPath() + "/provenance/latest/sample-provenance");
  }
  
}
