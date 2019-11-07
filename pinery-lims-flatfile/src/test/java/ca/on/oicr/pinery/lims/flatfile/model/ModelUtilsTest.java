package ca.on.oicr.pinery.lims.flatfile.model;

import java.time.format.DateTimeParseException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ModelUtilsTest {

  @Rule public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testConvertToDateValid() {
    Date date = ModelUtils.convertToDate("2015-12-02T15:34:00-04:00");
    Assert.assertNotNull(date);
  }

  @Test
  public void testConvertUtcToDateValid() {
    Date date = ModelUtils.convertToDate("2015-12-02T15:34:00.000Z");
    Assert.assertNotNull(date);
  }

  @Test
  public void testConvertToDateInvalid() {
    exception.expect(DateTimeParseException.class);
    ModelUtils.convertToDate("Not a date");
  }

  @Test
  public void testConvertNullToDate() {
    Assert.assertNull(ModelUtils.convertToDate(null));
  }

  @Test
  public void testConvertEmptyToDate() {
    Assert.assertNull(ModelUtils.convertToDate(""));
  }

  @Test
  public void testNullIfEmptyNull() {
    Assert.assertNull(ModelUtils.nullIfEmpty(null));
  }

  @Test
  public void testNullIfEmptyEmpty() {
    Assert.assertNull(ModelUtils.nullIfEmpty(""));
  }

  @Test
  public void testNullIfEmptyNonEmpty() {
    Assert.assertEquals(" ", ModelUtils.nullIfEmpty(" "));
  }

  @Test
  public void testNullIfZeroNull() {
    Assert.assertNull(ModelUtils.nullIfZero(null));
  }

  @Test
  public void testNullIfZeroZero() {
    Assert.assertNull(ModelUtils.nullIfZero(0));
  }

  @Test
  public void testNullIfZeroOne() {
    Assert.assertEquals(Integer.valueOf(1), ModelUtils.nullIfZero(1));
  }
}
