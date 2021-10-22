package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.service.RequisitionService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.RequisitionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = {"Requisitions"})
public class RequisitionResource {

  @Autowired private RequisitionService requisitionService;

  @GetMapping("/requisitions")
  @ApiOperation(
      value = "List all requisitions",
      response = RequisitionDto.class,
      responseContainer = "List")
  public List<RequisitionDto> getRequisitions() {
    List<Requisition> requisitions = requisitionService.getRequisitions();
    return requisitions.stream().map(Dtos::asDto).collect(Collectors.toList());
  }

  @GetMapping("/requisition/{id}")
  @ApiOperation(value = "Find requisition by ID", response = RequisitionDto.class)
  @ApiResponses({@ApiResponse(code = 404, message = "No requisition found")})
  public RequisitionDto getRequisition(
      @ApiParam(value = "ID of requisition to fetch") @PathVariable("id") Integer id) {
    Requisition requisition = requisitionService.getRequisition(id);
    if (requisition == null) {
      throw new RestException(
          HttpStatus.NOT_FOUND, String.format("No requisition with ID: %d", id));
    }
    return Dtos.asDto(requisition);
  }

  @GetMapping("/requisition")
  @ApiOperation(value = "Find requisition by name", response = RequisitionDto.class)
  @ApiResponses({
    @ApiResponse(code = 400, message = "Missing or invalid name parameter"),
    @ApiResponse(code = 404, message = "No requisition found")
  })
  public RequisitionDto getRequisitionByName(
      @ApiParam(value = "Name of requisition to fetch") @RequestParam("name") String name) {
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
