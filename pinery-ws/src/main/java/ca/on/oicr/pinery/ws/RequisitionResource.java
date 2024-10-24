package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.service.RequisitionService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.RequisitionDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Requisitions")
public class RequisitionResource {

  @Autowired
  private RequisitionService requisitionService;

  @GetMapping("/requisitions")
  @Operation(summary = "List all requisitions")
  public List<RequisitionDto> getRequisitions() {
    List<Requisition> requisitions = requisitionService.getRequisitions();
    return requisitions.stream().map(Dtos::asDto).collect(Collectors.toList());
  }

  @GetMapping("/requisition/{id}")
  @Operation(summary = "Find requisition by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No requisition found", content = @Content)
  })
  public RequisitionDto getRequisition(
      @Parameter(description = "ID of requisition to fetch") @PathVariable("id") Integer id) {
    Requisition requisition = requisitionService.getRequisition(id);
    if (requisition == null) {
      throw new RestException(
          HttpStatus.NOT_FOUND, String.format("No requisition with ID: %d", id));
    }
    return Dtos.asDto(requisition);
  }

  @GetMapping("/requisition")
  @Operation(summary = "Find requisition by name")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Missing or invalid name parameter", content = @Content),
      @ApiResponse(responseCode = "404", description = "No requisition found", content = @Content)
  })
  public RequisitionDto getRequisitionByName(
      @Parameter(description = "Name of requisition to fetch") @RequestParam("name") String name) {
    if (name == null || name.isEmpty()) {
      throw new RestException(HttpStatus.BAD_REQUEST, "Name parameter is required");
    }

    Requisition requisition = requisitionService.getRequisition(name);
    if (requisition == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No requisition found with name: " + name);
    }
    return Dtos.asDto(requisition);
  }
}
