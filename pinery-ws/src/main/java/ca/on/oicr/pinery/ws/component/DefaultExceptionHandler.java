package ca.on.oicr.pinery.ws.component;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.Failure;
import org.springframework.stereotype.Component;

/**
 * Handles all exceptions thrown while handling REST requests that are not caught by a different ExceptionMapper. A JSON 
 * representation of the Exception is returned in the Response. HTTP status will be set appropriately for JAX-RS and RESTEasy 
 * exceptions. Other (unexpected) Exceptions will be treated as internal server errors.
 */
@Provider
@Component
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
  
  @Override
  public Response toResponse(Exception exception) {
    RestError error = null;
    Status status = null;
    Map<String, String> data = new HashMap<>();
    
    if (exception instanceof WebApplicationException) {
      // Standard JAX-RS exceptions
      Response response = ((WebApplicationException) exception).getResponse();
      if (response != null) status = Status.fromStatusCode(response.getStatus());
    }
    else if (exception instanceof Failure) {
      // Deprecated RESTEasy exceptions
      Response response = ((Failure) exception).getResponse();
      if (response != null) status = Status.fromStatusCode(response.getStatus());
    }
    
    if (status == null) {
      // Unexpected exception type
      status = Status.INTERNAL_SERVER_ERROR;
      data.put("exceptionClass", exception.getClass().getName());
    }
    
    error = new RestError(status, exception.getMessage());
    error.setData(data);
    
    return Response.status(status).entity(error).build();
  }

}
