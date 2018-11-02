package ca.on.oicr.pinery.ws.component;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  private final RestError restError;

  public RestException(HttpStatus status, String detail) {
    super(detail);
    this.restError = new RestError(status, detail);
  }
  
  public RestError getRestError() {
    return restError;
  }

}
