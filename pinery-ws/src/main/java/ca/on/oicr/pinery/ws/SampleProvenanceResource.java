package ca.on.oicr.pinery.ws;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = {"Sample Provenance"})
public class SampleProvenanceResource {

    @Autowired
    private SampleProvenanceService sampleProvenanceService;

    @GetMapping("/sample-provenance")
    @ApiOperation(value = "Get all sample provenance records", response = SampleProvenanceDto.class, responseContainer = "List")
    public List<SampleProvenanceDto> getSamples() {

        List<SampleProvenance> sps = sampleProvenanceService.getSampleProvenance();
        
        if (sps == null || sps.isEmpty()) {
            return Collections.emptyList();
        }

        return Dtos.sampleProvenanceCollectionAsDto(sps);
    }
}
