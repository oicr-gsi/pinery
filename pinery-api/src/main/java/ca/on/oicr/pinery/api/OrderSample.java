package ca.on.oicr.pinery.api;

//Inheriting interface declarations from Sample and adding new interfaces 
public interface OrderSample extends Sample {

   public String getBarcode();

   public void setBarcode(String barcode);

}
