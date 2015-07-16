package ca.on.oicr.pinery.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class PineryClient {
	
	private final String pineryBaseUrl;
	private final Client client;
	private boolean open = false;
	
	/**
	 * Creates a new PineryClient to communicate with the Pinery web service at the specified URL. Note that 
	 * the client is considered "open" upon creation, and the close() method should be called when it is no 
	 * longer needed.
	 * 
	 * @param baseUrl
	 */
	public PineryClient(String baseUrl) {
		this.pineryBaseUrl = baseUrl;
		if (!pineryBaseUrl.endsWith("/")) baseUrl += "/";
		this.client = ClientBuilder.newBuilder().build();
		this.open = true;
	}
	
	/**
	 * Makes a GET request to the Pinery web service. The caller is responsible for calling close() on the returned 
	 * Response object once consumed. 
	 * 
	 * @param resourceUrl resource url relative to the Pinery base URL
	 * @return Response object containing the requested resource data. This object should be closed after the data 
	 * is extracted.
	 */
	protected Response callPinery(String resourceUrl) {
		checkIfOpen();
		
		String requestUrl = pineryBaseUrl + resourceUrl;
		WebTarget target = client.target(requestUrl);
		Response response = target.request().get();
		
		switch (response.getStatus()) {
		case 200:
			return response;
		default:
			// TODO: More useful (checked) exceptions
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + " for URL " + requestUrl);
		}
	}
	
	/**
	 * Creates a SampleClient to be used for retrieving Sample resources. This constructs a new SampleClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new SampleClient
	 */
	public SampleClient getSample() {
		checkIfOpen();
		return new SampleClient(this);
	}

	/**
	 * Creates a SampleProjectClient to be used for retrieving SampleProject resources. This constructs a new SampleProjectClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new SampleProjectClient
	 */
	public SampleProjectClient getSampleProject() {
		checkIfOpen();
		return new SampleProjectClient(this);
	}

	/**
	 * Creates a SampleTypeClient to be used for retrieving SampleType resources. This constructs a new SampleTypeClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new SampleTypeClient
	 */
	public SampleTypeClient getSampleType() {
		checkIfOpen();
		return new SampleTypeClient(this);
	}

	/**
	 * Creates a SequencerRunClient to be used for retrieving SequencerRun resources. This constructs a new SequencerRunClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new SequencerRunClient
	 */
	public SequencerRunClient getSequencerRun() {
		checkIfOpen();
		return new SequencerRunClient(this);
	}

	/**
	 * Creates a UserClient to be used for retrieving User resources. This constructs a new UserClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new UserClient
	 */
	public UserClient getUser() {
		checkIfOpen();
		return new UserClient(this);
	}

	/**
	 * Creates a AttributeNameClient to be used for retrieving AttributeName resources. This constructs a new AttributeNameClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new AttributeNameClient
	 */
	public AttributeNameClient getAttributeName() {
		checkIfOpen();
		return new AttributeNameClient(this);
	}

	/**
	 * Creates a ChangeLogClient to be used for retrieving ChangeLog resources. This constructs a new ChangeLogClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new ChangeLogClient
	 */
	public ChangeLogClient getChangeLog() {
		checkIfOpen();
		return new ChangeLogClient(this);
	}

	/**
	 * Creates a InstrumentClient to be used for retrieving Instrument resources. This constructs a new InstrumentClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new InstrumentClient
	 */
	public InstrumentClient getInstrument() {
		checkIfOpen();
		return new InstrumentClient(this);
	}

	/**
	 * Creates a InstrumentModelClient to be used for retrieving InstrumentModel resources. This constructs a new InstrumentModelClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new InstrumentModelClient
	 */
	public InstrumentModelClient getInstrumentModel() {
		checkIfOpen();
		return new InstrumentModelClient(this);
	}

	/**
	 * Creates a OrderClient to be used for retrieving Order resources. This constructs a new OrderClient every 
	 * time, so if it will be used for multiple GET requests, it's best to keep a reference to it.
	 * 
	 * @return a new OrderClient
	 */
	public OrderClient getOrder() {
		checkIfOpen();
		return new OrderClient(this);
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isClosed() {
		return !open;
	}
	
	private void checkIfOpen() {
		if (!open) throw new IllegalStateException("This PineryClient is closed, and can no longer be used.");
	}
	
	/**
	 * Closes the client instance, and its contained resources. Attempts to use a closed PineryClient will result in 
	 * an IllegalStateException being thrown. Subsequent calls to this method have no effect, and are ignored.
	 */
	public void close() {
		if (open && client != null) {
			open = false;
			client.close();
		}
	}
	
}
