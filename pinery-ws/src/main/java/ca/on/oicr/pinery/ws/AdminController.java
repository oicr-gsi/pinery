package ca.on.oicr.pinery.ws;

import ca.on.oicr.pinery.service.impl.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Admin"})
public class AdminController {

  @Autowired private Cache cache;

  @PostMapping("/updatecache")
  @ApiOperation(value = "Forces a cache refresh")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Success"),
    @ApiResponse(code = 500, message = "Error updating cache")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateCache() {
    cache.update();
  }
}
