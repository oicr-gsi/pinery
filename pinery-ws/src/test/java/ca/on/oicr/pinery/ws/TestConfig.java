package ca.on.oicr.pinery.ws;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.pinery.service.SampleProvenanceService;

@Configuration
public class TestConfig {

  @Bean
  @Primary
  public Lims mockLims() {
    return mock(Lims.class);
  }

  @Bean
  @Primary
  public SampleProvenanceService mockSampleProvenanceService() {
    return mock(SampleProvenanceService.class);
  }

  @Bean
  @Primary
  public LaneProvenanceService mockLaneProvenanceService() {
    return mock(LaneProvenanceService.class);
  }

}
