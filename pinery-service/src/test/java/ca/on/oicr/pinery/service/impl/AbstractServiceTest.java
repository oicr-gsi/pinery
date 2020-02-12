package ca.on.oicr.pinery.service.impl;

import org.junit.BeforeClass;

public abstract class AbstractServiceTest {

  @BeforeClass
  public static void init() {
    String path =
        AbstractServiceTest.class.getClassLoader().getResource("test.properties").getPath();
    System.setProperty("pinery.propertiesFile", "file:" + path);
  }
}
