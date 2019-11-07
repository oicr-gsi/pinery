package ca.on.oicr.pinery.lims.gsle;

import static org.junit.Assert.*;

import org.junit.Test;

public class TemporaryBoxPositionTest {

  @Test
  public void testSetPositions() {
    TemporaryBoxPosition sut = new TemporaryBoxPosition();
    sut.setX("5");
    assertNull(sut.getPosition());
    sut = new TemporaryBoxPosition();
    sut.setY("5");
    assertNull(sut.getPosition());
    sut.setX("8");
    assertNotNull(sut.getPosition());
    assertEquals("F09", sut.getPosition());
  }
}
