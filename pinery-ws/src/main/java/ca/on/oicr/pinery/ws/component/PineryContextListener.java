package ca.on.oicr.pinery.ws.component;

import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.jmx.JmxCollector;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.management.MalformedObjectNameException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PineryContextListener implements ServletContextListener {

  @Override
  public void contextDestroyed(ServletContextEvent event) {}

  @Override
  public void contextInitialized(ServletContextEvent event) {
    // Export all JVM HotSpot stats to Prometheus
    DefaultExports.initialize();
    try {
      URL yamlConfig =
          Thread.currentThread().getContextClassLoader().getResource("tomcat-prometheus.yml");
      if (yamlConfig == null) {
        throw new IllegalStateException("Prometheus configuration not found");
      }

      new JmxCollector(new File(yamlConfig.getFile())).register();
    } catch (MalformedObjectNameException | IOException e) {
      throw new IllegalStateException("Failed to load Prometheus configuration.", e);
    }
  }
}
