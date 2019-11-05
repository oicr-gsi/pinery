package ca.on.oicr.pinery.lims.gsle;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.BoxPosition;
import ca.on.oicr.pinery.lims.DefaultBox;
import ca.on.oicr.pinery.lims.DefaultBoxPosition;
import ca.on.oicr.pinery.lims.GsleBox;
import java.util.HashSet;
import java.util.Set;

/** Holds details of the box and a single position to be combined later */
public class TemporaryBoxPosition extends GsleBox implements BoxPosition {

  private String position;
  private String sampleId;
  private Integer x;
  private Integer y;

  @Override
  public String getPosition() {
    return position;
  }

  @Override
  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String getSampleId() {
    return sampleId;
  }

  @Override
  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  public Box getBox() {
    Box box = new DefaultBox();
    box.setId(this.getId());
    box.setName(this.getName());
    box.setDescription(this.getDescription());
    box.setLocation(this.getLocation());
    box.setRows(this.getRows());
    box.setColumns(this.getColumns());
    box.setPositions(new HashSet<BoxPosition>());
    return box;
  }

  public BoxPosition getBoxPosition() {
    BoxPosition pos = new DefaultBoxPosition();
    pos.setPosition(this.getPosition());
    pos.setSampleId(this.getSampleId());
    return pos;
  }

  @Override
  public Set<BoxPosition> getPositions() {
    throw new UnsupportedOperationException("Unintended use of temporary object");
  }

  @Override
  public void setPositions(Set<BoxPosition> positions) {
    throw new UnsupportedOperationException("Unintended use of temporary object");
  }

  public void setX(String x) {
    setPosition(cleanInt(x), this.y);
  }

  public void setY(String y) {
    setPosition(this.x, cleanInt(y));
  }

  private static Integer cleanInt(String i) {
    // query includes empty String instead of "0"
    return i.isEmpty() ? 0 : Integer.parseInt(i);
  }

  private void setPosition(Integer x, Integer y) {
    this.x = x;
    this.y = y;
    if (x != null && y != null) {
      setPosition(makePositionString(x, y));
    }
  }

  private static String makePositionString(int x, int y) {
    return getRowLetter(y) + getColumnNumber(x);
  }

  private static String getRowLetter(int y) {
    if (y >= 0 && y <= 25) {
      return String.valueOf((char) (y + 65));
    } else {
      throw new IndexOutOfBoundsException("y value must be between 0 (A) and 25 (Z)");
    }
  }

  private static String getColumnNumber(int x) {
    int oneBased = x + 1;
    return (oneBased < 10) ? ("0" + oneBased) : String.valueOf(oneBased);
  }
}
