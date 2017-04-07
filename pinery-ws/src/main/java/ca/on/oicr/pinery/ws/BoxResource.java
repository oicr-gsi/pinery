package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.service.BoxService;
import ca.on.oicr.ws.dto.BoxDto;
import ca.on.oicr.ws.dto.Dtos;

@Component
@Path("/")
@Api(value = "box")
public class BoxResource {
  
  @Autowired
  private BoxService boxService;

  @GET
  @Produces({ "application/json" })
  @Path("/boxes")
  @ApiOperation(value = "List all boxes", response = ca.on.oicr.ws.dto.BoxDto.class, responseContainer = "List")
  public List<BoxDto> getBoxes() {
    List<Box> boxes = boxService.getBoxes();
    return Dtos.asDtoList(boxes);
  }

}
