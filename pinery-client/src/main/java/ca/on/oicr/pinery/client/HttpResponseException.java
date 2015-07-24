package ca.on.oicr.pinery.client;

import javax.ws.rs.core.Response.Status;

/**
 * Exception class to encapsulate any HTTP response status other than 200 (OK)
 * 
 * @author dcooke
 *
 */
public class HttpResponseException extends Exception {
	
	private static final long serialVersionUID = -7430326891153732863L;
	
	private Status status;
	private String requestUrl;
	
	public HttpResponseException() {
		this(null, -1);
	}
	
	/**
	 * Constructs an HttpResponseException representing the specified status code
	 * 
	 * @param status the HTTP status code
	 */
	public HttpResponseException(int status) {
		this(null, status);
	}
	
	/**
	 * Constructs an HttpResponseException representing an unknown status code returned from the 
	 * specified URL
	 * 
	 * @param requestUrl
	 */
	public HttpResponseException(String requestUrl) {
		this(requestUrl, -1);
	}
	
	/**
	 * Constructs an HttpResponseException representing the specified status code returned from 
	 * the specified URL
	 * 
	 * @param requestUrl the URL requested
	 * @param status the HTTP status code
	 */
	public HttpResponseException(String requestUrl, int status) {
		this.status = Status.fromStatusCode(status);
		this.requestUrl = requestUrl;
	}
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP Request");
		if (requestUrl != null) {
			sb.append(" to ").append(requestUrl);
		}
		sb.append(" failed");
		if (status != null) {
			sb.append(" with status ")
				.append(status.getStatusCode())
				.append(" -- ")
				.append(status.getReasonPhrase());
		}
		return sb.toString();
	}

	/**
	 * @return the status that caused this exception. Contains the status code, family, and 
	 * reason phrase 
	 */
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * @return the url requested, which returned the status code that caused this exception
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}
