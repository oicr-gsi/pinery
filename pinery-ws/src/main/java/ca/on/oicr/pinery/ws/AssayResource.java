package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.api.Assay;
import ca.on.oicr.pinery.service.AssayService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.AssayDto;
import ca.on.oicr.ws.dto.Dtos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
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
@Tag(name = "Assays")
public class AssayResource {

  @Autowired
  private AssayService assayService;

  @GetMapping("/assays")
  @Operation(summary = "List all assays")
  public List<AssayDto> getAssays() {
    List<Assay> assays = assayService.getAssays();
    return assays.stream().map(Dtos::asDto).collect(Collectors.toList());
  }

  @GetMapping("/assay/{id}")
  @Operation(summary = "Find assay by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No assay found", content = @Content)
  })
  public AssayDto getAssay(
      @Parameter(description = "ID of assay to fetch") @PathVariable("id") Integer id) {
    Assay assay = assayService.getAssay(id);
    if (assay == null) {
      throw new RestException(HttpStatus.NOT_FOUND, String.format("No assay with ID: %d", id));
    }
    return Dtos.asDto(assay);
  }
}
