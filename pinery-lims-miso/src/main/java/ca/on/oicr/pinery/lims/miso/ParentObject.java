package ca.on.oicr.pinery.lims.miso;

public interface ParentObject<T> {

  public Integer getId();
  public void addChild(T child);
  
}
