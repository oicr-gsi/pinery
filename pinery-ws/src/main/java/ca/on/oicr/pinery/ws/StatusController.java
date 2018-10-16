package ca.on.oicr.pinery.ws;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Component;

import ca.on.oicr.gsi.status.ConfigurationSection;
import ca.on.oicr.gsi.status.Header;
import ca.on.oicr.gsi.status.NavigationMenu;
import ca.on.oicr.gsi.status.SectionRenderer;
import ca.on.oicr.gsi.status.ServerConfig;
import ca.on.oicr.gsi.status.StatusPage;

@Component
@Path("/")
public class StatusController {
  public static final ServerConfig SERVER_CONFIG = new ServerConfig() {

    @Override
    public Stream<NavigationMenu> navigation() {
      return Stream.empty();
    }

    @Override
    public String name() {
      return "Pinery";
    }

    @Override
    public Stream<Header> headers() {
      return Stream.empty();
    }
  };

  @GET
  public Response status(@Context HttpServletResponse response) throws IOException {
    return Response.ok(new StreamingOutput() {

      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        new StatusPage(SERVER_CONFIG) {

          @Override
          public Stream<ConfigurationSection> sections() {
            return Stream.empty();
          }

          @Override
          protected void emitCore(SectionRenderer renderer) throws XMLStreamException {
            // No information to display.
          }
        }.renderPage(output);
      }

    }, "text/html;charset=utf-8").build();
  }
}
