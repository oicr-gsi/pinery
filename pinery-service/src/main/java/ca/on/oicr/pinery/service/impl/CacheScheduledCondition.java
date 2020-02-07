package ca.on.oicr.pinery.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CacheScheduledCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String uriString = context.getEnvironment().getProperty("pinery.propertiesFile");
    if (uriString == null) {
      throw new IllegalStateException("pinery.propertiesFile not set");
    }

    URI uri;
    try {
      uri = new URI(uriString);
    } catch (URISyntaxException e1) {
      throw new IllegalStateException("Invalid file URI: " + uriString);
    }
    File file = new File(uri);
    Properties properties = new Properties();
    try (InputStream input = new FileInputStream(file)) {
      properties.load(input);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Properties file not found: " + uriString);
    } catch (IOException e) {
      throw new IllegalStateException("Error reading properties file", e);
    }

    String enabledProp = properties.getProperty("pinery.cache.enabled");
    String intervalProp = properties.getProperty("pinery.cache.interval");

    if (enabledProp == null || intervalProp == null) {
      throw new IllegalStateException("Cache properties not found in properties file");
    }

    boolean enabled = Boolean.parseBoolean(enabledProp);
    int interval = Integer.parseInt(intervalProp);

    return enabled && interval > 0;
  }
}
