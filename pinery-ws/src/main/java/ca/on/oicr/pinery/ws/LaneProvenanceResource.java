package ca.on.oicr.pinery.ws;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.LaneProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author mlaszloffy
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Lane Provenance"})
public class LaneProvenanceResource {

    @Autowired
    private LaneProvenanceService laneProvenanceService;

    @GetMapping(path = "/lane-provenance")
    @ApiOperation(value = "Get all lane provenance records", response = LaneProvenanceDto.class, responseContainer = "List")
    public List<LaneProvenanceDto> getLanes() {

        List<LaneProvenance> lps = laneProvenanceService.getLaneProvenance();

        if (lps == null || lps.isEmpty()) {
            return Collections.emptyList();
        }

        return Dtos.laneProvenanceCollectionAsDto(lps);
    }
}
