package ca.on.oicr.pinery.ws;

import org.junit.BeforeClass;

public abstract class AbstractResourceTest {

  @BeforeClass
  public static void init() {
    String path =
        AbstractResourceTest.class.getClassLoader().getResource("test.properties").getPath();
    System.setProperty("pinery.propertiesFile", "file:" + path);
  }
}
