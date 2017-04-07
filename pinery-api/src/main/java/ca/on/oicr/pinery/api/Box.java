package ca.on.oicr.pinery.api;

import java.util.Set;

public interface Box {
  
  public Long getId();
  
  public void setId(Long id);
  
  public String getName();
  
  public void setName(String name);
  
  public String getDescription();
  
  public void setDescription(String description);
  
  public String getLocation();
  
  public void setLocation(String location);
  
  public Integer getRows();
  
  public void setRows(Integer rows);
  
  public Integer getColumns();
  
  public void setColumns(Integer columns);
  
  public Set<BoxPosition> getPositions();
  
  public void setPositions(Set<BoxPosition> positions);

}
