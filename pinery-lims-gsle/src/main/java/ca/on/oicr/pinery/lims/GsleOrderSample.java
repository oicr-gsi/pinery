package ca.on.oicr.pinery.lims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsleOrderSample extends DefaultOrderSample {

   private static final Logger log = LoggerFactory.getLogger(GsleOrderSample.class);

   public void setIdString(String idString) {
      if (idString != null) {
         setId(Integer.parseInt(idString));
      }
   }
}
