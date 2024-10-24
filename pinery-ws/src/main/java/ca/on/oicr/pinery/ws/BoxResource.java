package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.service.BoxService;
import ca.on.oicr.ws.dto.BoxDto;
import ca.on.oicr.ws.dto.Dtos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Boxes")
public class BoxResource {

  @Autowired
  private BoxService boxService;

  @GetMapping("/boxes")
  @Operation(summary = "List all boxes")
  public List<BoxDto> getBoxes() {
    List<Box> boxes = boxService.getBoxes();
    return Dtos.asDtoList(boxes);
  }
}
