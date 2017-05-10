package ca.on.oicr.pinery.ws.component;

import java.io.IOException;
import java.net.URL;

import javax.management.MalformedObjectNameException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.jmx.JmxCollector;

public class PineryContextListener implements ServletContextListener {
  private static final Logger log = LoggerFactory.getLogger(PineryContextListener.class);

  @Override
  public void contextDestroyed(ServletContextEvent event) {
  }

  @Override
  public void contextInitialized(ServletContextEvent event) {
    // Export all JVM HotSpot stats to Prometheus
    DefaultExports.initialize();
    try {
      URL yamlConfig = event.getServletContext().getResource("classpath:tomcat-prometheus.yml");
      if (yamlConfig != null) {
        new JmxCollector(yamlConfig.getFile()).register();
      }
    } catch (MalformedObjectNameException | IOException e) {
      log.error("Failed to load Prometheus configuration.", e);
    }
  }

}
