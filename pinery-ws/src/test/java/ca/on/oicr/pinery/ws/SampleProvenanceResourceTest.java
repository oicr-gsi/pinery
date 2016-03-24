package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.provenance.api.model.SampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.*;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author mlaszloffy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-spring-servlet.xml")
public class SampleProvenanceResourceTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SampleProvenanceService sampleProvenanceService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void resetMocks() {
        Mockito.reset(sampleProvenanceService);
    }

    @Test
    public void testEmptyResultSet() throws Exception {
        List<SampleProvenance> sps = Collections.EMPTY_LIST;

        when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

        mockMvc.perform(get("/sample-provenance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testJodaTimeObjectMapper() throws Exception {
        List<SampleProvenance> sps = new ArrayList<>();
        SampleProvenanceDto sp = new SampleProvenanceDto();
        sp.setLastModified(DateTime.parse("2016-01-01T00:00:00.000Z"));
        sps.add(sp);

        when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

        mockMvc.perform(get("/sample-provenance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].lastModified", everyItem(equalTo("2016-01-01T00:00:00.000Z"))));
    }

    @Test
    public void testJsonArraySize() throws Exception {
        List<SampleProvenance> sps = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SampleProvenanceDto sp = new SampleProvenanceDto();
            sps.add(sp);
        }

        when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

        mockMvc.perform(get("/sample-provenance").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(100)));
    }

}
