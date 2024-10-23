package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.service.impl.Cache;
import ca.on.oicr.pinery.ws.component.RestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Admin")
public class AdminController {

  @Autowired
  private Cache cache;

  @PostMapping("/updatecache")
  @Operation(summary = "Forces a cache refresh")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Caching not enabled"),
      @ApiResponse(responseCode = "500", description = "Error updating cache")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateCache() {
    if (!cache.isEnabled()) {
      throw new RestException(HttpStatus.BAD_REQUEST, "Caching not enabled");
    }
    cache.update();
  }
}
