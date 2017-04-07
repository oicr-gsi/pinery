package ca.on.oicr.pinery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.service.BoxService;

@Service
public class DefaultBoxService implements BoxService {

  @Autowired
  private Lims lims;
  
  @Override
  public List<Box> getBoxes() {
    return lims.getBoxes();
  }

}
