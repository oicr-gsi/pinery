package ca.on.oicr.pinery.ws.component;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is required for Swagger to work
 */
@Component
@Provider
@ServerInterceptor
public class ResteasyServletContext implements ContainerRequestFilter {

  @Autowired
  ServletContext sc;

  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    ResteasyProviderFactory.getContextDataMap().put(ServletContext.class, sc);
  }

}
