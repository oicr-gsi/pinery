package ca.on.oicr.pinery.ws;

import ca.on.oicr.gsi.status.ConfigurationSection;
import ca.on.oicr.gsi.status.Header;
import ca.on.oicr.gsi.status.NavigationMenu;
import ca.on.oicr.gsi.status.SectionRenderer;
import ca.on.oicr.gsi.status.ServerConfig;
import ca.on.oicr.gsi.status.StatusPage;
import ca.on.oicr.pinery.service.impl.Cache;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
@ApiIgnore
public class StatusController {
  public static final ServerConfig SERVER_CONFIG =
      new ServerConfig() {

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

        @Override
        public String documentationUrl() {
          return "swagger-ui.html";
        }
      };

  @Autowired private Cache cache;

  @Value("${pinery.cache.interval:900000}")
  private int cacheInterval;

  @GetMapping
  public void showStatus(HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try (OutputStream output = response.getOutputStream()) {
      new StatusPage(SERVER_CONFIG) {

        @Override
        public Stream<ConfigurationSection> sections() {
          return Stream.empty();
        }

        @Override
        protected void emitCore(SectionRenderer renderer) throws XMLStreamException {
          if (cache.isEnabled()) {
            renderer.line("Cache interval (seconds)", cacheInterval);
            renderer.lineSpan("Cache last updated", cache.getLastUpdateTime());
          } else {
            renderer.line("Cache", "Disabled");
          }
        }
      }.renderPage(output);
    }
  }
}
