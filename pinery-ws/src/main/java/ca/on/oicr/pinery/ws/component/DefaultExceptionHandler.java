package ca.on.oicr.pinery.ws.component;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles all exceptions thrown while handling REST requests. A JSON representation of the
 * Exception is returned in the response. HTTP status will be set appropriately for Spring-generated
 * exceptions, and as specified in RestExceptions. Other (unexpected) Exceptions will be treated as
 * internal server errors.
 */
@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> toResponse(Exception exception) {
    RestError error = null;

    if (exception instanceof RestException) {
      error = ((RestException) exception).getRestError();
    } else {
      // Unexpected exception type
      Map<String, String> data = new HashMap<>();
      data.put("exceptionClass", exception.getClass().getName());
      error = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
      error.setData(data);
    }

    HttpStatus status = HttpStatus.valueOf(error.getStatus());
    logError(exception, status);
    return new ResponseEntity<>(error, new HttpHeaders(), status);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception exception,
      Object body,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    logError(exception, status);
    RestError error = null;
    if (status instanceof HttpStatus httpStatus) {
      error = new RestError(httpStatus, exception.getLocalizedMessage());
    } else {
      String reasonPhrase = null;
      if (status.is4xxClientError()) {
        reasonPhrase = "Client Error";
      } else if (status.is5xxServerError()) {
        reasonPhrase = "Server Error";
      } else {
        reasonPhrase = "Unknown Error";
      }
      error = new RestError(status.value(), reasonPhrase, exception.getLocalizedMessage());
    }
    return new ResponseEntity<>(error, headers, status);
  }

  private static void logError(Throwable exception, HttpStatusCode status) {
    if (status.is5xxServerError()) {
      log.error("Error handling REST request", exception);
    } else {
      log.debug("Non-server error handling REST request", exception);
    }
  }
}
