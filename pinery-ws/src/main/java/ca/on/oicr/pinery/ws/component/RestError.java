package ca.on.oicr.pinery.ws.component;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Representation of an error regarding a REST request, intended to be sent back to the client as JSON
 */
public class RestError {
  
  private int status;
  private String message;
  private String detail;
  private Map<String, String> data;
  
  /**
   * Creates a new RestError
   */
  public RestError() {
    
  }
  
  /**
   * Creates a new RestError based on response status and a detail message
   * 
   * @param status the response status to represent. Will be used to set the status code and message
   * @param detail detailed error message
   */
  public RestError(HttpStatus status, String detail) {
    this.status = status.value();
    this.message = status.getReasonPhrase();
    this.detail = detail;
  }

  /**
   * @return the HTTP status code associated with this error
   */
  public int getStatus() {
    return status;
  }

  /**
   * Sets the HTTP status code associated with this error
   * 
   * @param status
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * @return the summary message - usually the HTTP status String
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the summary message - usually the HTTP status String
   * 
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the detail message
   */
  public String getDetail() {
    return detail;
  }

  /**
   * Sets the detail message, which should describe the error using specifics if possible
   * 
   * @param detail
   */
  public void setDetail(String detail) {
    this.detail = detail;
  }

  /**
   * @return a map containing any additional data to include with the error
   */
  public Map<String, String> getData() {
    return data;
  }

  /**
   * Sets additional data to be included with the error
   * 
   * @param data
   */
  public void setData(Map<String, String> data) {
    this.data = data;
  }

}
