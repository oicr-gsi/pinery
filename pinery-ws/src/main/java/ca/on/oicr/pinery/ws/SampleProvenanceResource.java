package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
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

import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleProvenanceDto;

import io.swagger.annotations.ApiResponses;
import javax.ws.rs.NotFoundException;

@Component
@Path("/")
@Api(value = "sample provenance")
public class SampleProvenanceResource {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private SampleProvenanceService sampleProvenanceService;

    @GET
    @Produces({"application/json"})
    @Path("/sample-provenance")
    @ApiOperation(value = "Get all sample provenance records", response = SampleProvenanceDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid parameter"),
        @ApiResponse(code = 404, message = "No records found")
    })
    public List<SampleProvenanceDto> getSamples() {

        List<SampleProvenance> sps = sampleProvenanceService.getSampleProvenance();
        
        if (sps == null || sps.isEmpty()) {
            throw new NotFoundException("No records found");
        }

        return Dtos.sampleProvenanceCollectionAsDto(sps);
    }
}
