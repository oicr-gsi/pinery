package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.service.BoxService;
import ca.on.oicr.ws.dto.BoxDto;
import ca.on.oicr.ws.dto.Dtos;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Boxes"})
public class BoxResource {

  @Autowired private BoxService boxService;

  @GetMapping("/boxes")
  @ApiOperation(
      value = "List all boxes",
      response = ca.on.oicr.ws.dto.BoxDto.class,
      responseContainer = "List")
  public List<BoxDto> getBoxes() {
    List<Box> boxes = boxService.getBoxes();
    return Dtos.asDtoList(boxes);
  }
}
