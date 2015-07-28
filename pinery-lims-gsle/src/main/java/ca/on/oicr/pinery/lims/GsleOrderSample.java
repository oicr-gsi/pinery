package ca.on.oicr.pinery.lims;

public class GsleOrderSample extends DefaultOrderSample {

//   private static final Logger log = LoggerFactory.getLogger(GsleOrderSample.class);

   public void setIdString(String idString) {
      if (idString != null) {
         setId(Integer.parseInt(idString));
      }
   }
}
