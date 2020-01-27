package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.service.BoxService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBoxService implements BoxService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<Box> getBoxes() {
    return lims.getBoxes();
  }
}
