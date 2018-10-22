package ca.on.oicr.pinery.ws;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public class PineryUtils {

  private PineryUtils() {
    throw new IllegalStateException("Static util class not intended for instantiation");
  }
  
  public static URI getBaseUri(UriComponentsBuilder uriBuilder) {
    return uriBuilder.build().toUri();
  }
  
  public static String buildSampleUrl(URI baseUri, String id) {
    return buildUrl(baseUri, "/sample/{id}", id);
  }
  
  public static String buildUserUrl(URI baseUri, Integer id) {
    return buildUrl(baseUri, "/user/{id}", id);
  }
  
  public static String buildInstrumentUrl(URI baseUri, Integer id) {
    return buildUrl(baseUri, "/instrument/{id}", id);
  }
  
  public static String buildInstrumentModelUrl(URI baseUri, Integer id) {
    return buildUrl(baseUri, "/instrumentmodel/{id}", id);
  }
  
  public static String buildModelInstrumentsUrl(URI baseUri, Integer modelId) {
    return buildUrl(baseUri, "/instrumentmodel/{id}/instruments", modelId);
  }
  
  public static String buildOrderUrl(URI baseUri, Integer id) {
    return buildUrl(baseUri, "/order/{id}", id);
  }
  
  public static String buildRunUrl(URI baseUri, Integer id) {
    return buildUrl(baseUri, "/sequencerrun/{id}", id);
  }
  
  private static String buildUrl(URI baseUri, String path, Object... variables) {
    return UriComponentsBuilder.fromUri(baseUri).path(path).buildAndExpand(variables).toUriString();
  }

}
