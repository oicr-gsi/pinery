package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.ws.dto.LaneProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.ws.dto.Dtos;

import io.swagger.annotations.ApiResponses;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author mlaszloffy
 */
@Component
@Path("/")
@Api(value = "lane provenance")
public class LaneProvenanceResource {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private LaneProvenanceService laneProvenanceService;

    @GET
    @Produces({"application/json"})
    @Path("/lane-provenance")
    @ApiOperation(value = "Get all lane provenance records", response = LaneProvenanceDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid parameter"),
        @ApiResponse(code = 404, message = "No records found")
    })
    public List<LaneProvenanceDto> getLanes() {

        List<LaneProvenance> lps = laneProvenanceService.getLaneProvenance();

        if (lps == null || lps.isEmpty()) {
            throw new NotFoundException("No records found");
        }

        return Dtos.laneProvenanceCollectionAsDto(lps);
    }
}
