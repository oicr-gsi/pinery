package ca.on.oicr.pinery.client;

import javax.ws.rs.core.Response.Status;

public class HttpResponseException extends Exception {
	
	private static final long serialVersionUID = -7430326891153732863L;
	
	private Status status;
	private String requestUrl;
	
	public HttpResponseException() {
		this(null, -1);
	}
	
	public HttpResponseException(int status) {
		this(null, status);
	}
	
	public HttpResponseException(String requestUrl, int status) {
		this.status = Status.fromStatusCode(status);
		this.requestUrl = requestUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP Request");
		if (requestUrl != null) {
			sb.append(" to ").append(requestUrl);
		}
		sb.append(" failed");
		if (status != null) {
			sb.append(" with status ")
				.append(status.getStatusCode())
				.append(": ")
				.append(status.getReasonPhrase());
		}
		return sb.toString();
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}
