package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class BoxDto {

  private Long id;
  private String name;
  private String description;
  private String location;
  private Integer rows;
  private Integer columns;
  private Set<BoxPositionDto> positions;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  @JsonProperty("row_count")
  public Integer getRows() {
    return rows;
  }
  
  public void setRows(Integer rows) {
    this.rows = rows;
  }
  
  @JsonProperty("column_count")
  public Integer getColumns() {
    return columns;
  }
  
  public void setColumns(Integer columns) {
    this.columns = columns;
  }
  
  @JsonProperty("samples")
  public Set<BoxPositionDto> getPositions() {
    return positions;
  }
  
  public void setPositions(Set<BoxPositionDto> positions) {
    this.positions = positions;
  }

}
