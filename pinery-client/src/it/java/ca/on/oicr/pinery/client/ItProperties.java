package ca.on.oicr.pinery.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ItProperties {

  Properties properties;

  public ItProperties() throws FileNotFoundException, IOException {
    properties = new Properties();
    properties.load(new FileReader("src/it/resources/it.properties"));
  }

  public Integer getInt(String key) {
    return new Integer(properties.getProperty(key));
  }

  public boolean getBoolean(String key) {
    return Boolean.parseBoolean(key);
  }

  public String get(String key) {
    return properties.getProperty(key);
  }

  public PineryClient getPineryClient() {
    String urlArg = System.getProperty("pinery-url");
    return new PineryClient(urlArg == null ? get("pinery.baseUrl") : urlArg);
  }
}
