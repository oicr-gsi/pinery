package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.on.oicr.pinery.lims.flatfile.dao.exception.NonUniqueKeyException;
import ca.on.oicr.pinery.lims.flatfile.dao.exception.ParseException;

public class DaoUtilsTest {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testGetExpectedSingleResultEmpty() {
    List<String> list = new ArrayList<>();
    Assert.assertNull(DaoUtils.getExpectedSingleResult(list));
  }
  
  @Test
  public void testGetExpectedSingleResultOne() {
    List<String> list = new ArrayList<>();
    list.add("One");
    Assert.assertEquals("One", DaoUtils.getExpectedSingleResult(list));
  }
  
  @Test
  public void testGetExpectedSingleResultMulti() {
    List<String> list = new ArrayList<>();
    list.add("One");
    list.add("Two");
    exception.expect(NonUniqueKeyException.class);
    DaoUtils.getExpectedSingleResult(list);
  }
  
  @Test
  public void testParseListNoItems() {
    List<String> parsed = DaoUtils.parseList("[]");
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseListNull() {
    List<String> parsed = DaoUtils.parseList(null);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseListEmptyString() {
    List<String> parsed = DaoUtils.parseList("");
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseListNotEnclosed() {
    exception.expect(ParseException.class);
    DaoUtils.parseList("1,2,3");
  }
  
  @Test
  public void testParseListSimple() {
    String string = "[1,2,3]";
    List<String> parsed = DaoUtils.parseList(string);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(3, parsed.size());
    Assert.assertEquals("1", parsed.get(0));
    Assert.assertEquals("2", parsed.get(1));
    Assert.assertEquals("3", parsed.get(2));
  }
  
  @Test
  public void testParseListComplex() {
    String string = "[{innerList=[1,2,3]},{innerList=[4,5,6]},{innerList=[7,8,9]}]";
    List<String> parsed = DaoUtils.parseList(string);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(3, parsed.size());
    Assert.assertEquals("{innerList=[1,2,3]}", parsed.get(0));
    Assert.assertEquals("{innerList=[4,5,6]}", parsed.get(1));
    Assert.assertEquals("{innerList=[7,8,9]}", parsed.get(2));
  }
  
  @Test
  public void testParseListBadNesting() {
    String string = "[{innerList=[1,2,3]},{]";
    exception.expect(ParseException.class);
    DaoUtils.parseList(string);
  }
  
  @Test
  public void testParseKVNoItems() {
    Map<String,String> parsed = DaoUtils.parseKeyValuePairs("{}");
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseKVNull() {
    Map<String,String> parsed = DaoUtils.parseKeyValuePairs(null);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseKVEmptyString() {
    Map<String,String> parsed = DaoUtils.parseKeyValuePairs("");
    Assert.assertNotNull(parsed);
    Assert.assertEquals(0, parsed.size());
  }
  
  @Test
  public void testParseKVNotEnclosed() {
    exception.expect(ParseException.class);
    DaoUtils.parseKeyValuePairs("One=1|Two=2|Three=3");
  }
  
  @Test
  public void testParseKVSimple() {
    String string = "{One=1|Two=2|Three=3}";
    Map<String,String> parsed = DaoUtils.parseKeyValuePairs(string);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(3, parsed.size());
    Assert.assertEquals("1", parsed.get("One"));
    Assert.assertEquals("2", parsed.get("Two"));
    Assert.assertEquals("3", parsed.get("Three"));
  }
  
  @Test
  public void testParseKVComplex() {
    String string = "{One=[{name=1|val=1},{name=2|val=2}]|Two=[{name=3|val=3},{name=4|val=4}]|Three=[{name=5|val=5},{name=6|val=6}]}";
    Map<String,String> parsed = DaoUtils.parseKeyValuePairs(string);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(3, parsed.size());
    Assert.assertEquals("[{name=1|val=1},{name=2|val=2}]", parsed.get("One"));
    Assert.assertEquals("[{name=3|val=3},{name=4|val=4}]", parsed.get("Two"));
    Assert.assertEquals("[{name=5|val=5},{name=6|val=6}]", parsed.get("Three"));
  }
  
  @Test
  public void testParseKVMissingValue() {
    String string = "{One=1|Two}";
    exception.expect(ParseException.class);
    DaoUtils.parseKeyValuePairs(string);
  }
  
  @Test
  public void testParseKVInvalidKey() {
    String string = "{{One=I}=1|Two=2|Three=3}";
    exception.expect(ParseException.class);
    DaoUtils.parseKeyValuePairs(string);
  }
  
}
