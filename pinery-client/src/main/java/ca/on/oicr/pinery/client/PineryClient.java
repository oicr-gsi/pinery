package ca.on.oicr.pinery.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class PineryClient {
	
	private final String pineryBaseUrl;
	private final Client client;
	private boolean open = false;
	
	// Individual resource clients lazily-loaded upon first request requested 
	private SampleClient samples;
	private SampleProjectClient sampleProjects;
	private SampleTypeClient sampleTypes;
	private SequencerRunClient sequencerRuns;
	private UserClient users;
	private AttributeNameClient attributeNames;
	private ChangeLogClient changeLogs;
	private InstrumentClient instruments;
	private InstrumentModelClient instrumentModels;
	private OrderClient orders;
	
	/**
	 * Creates a new PineryClient to communicate with the Pinery web service at the specified URL. Note that 
	 * the client is considered "open" upon creation, and the close() method should be called when it is no 
	 * longer needed.
	 * 
	 * @param baseUrl
	 */
	public PineryClient(String baseUrl) {
		this.pineryBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
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
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	protected Response callPinery(String resourceUrl) throws HttpResponseException {
		checkIfOpen();
		
		String requestUrl = pineryBaseUrl + resourceUrl;
		WebTarget target = client.target(requestUrl);
		Response response = target.request().get();
		
		switch (response.getStatus()) {
		case 200:
			return response;
		default:
			throw new HttpResponseException(requestUrl, response.getStatus());
		}
	}
	
	/**
	 * Returns a SampleClient to be used for retrieving Sample resources from this Pinery client.
	 * 
	 * @return SampleClient
	 */
	public SampleClient getSample() {
		checkIfOpen();
		if (samples == null) samples = new SampleClient(this);
		return samples;
	}

	/**
	 * Returns a SampleProjectClient to be used for retrieving SampleProject resources from this Pinery client.
	 * 
	 * @return SampleProjectClient
	 */
	public SampleProjectClient getSampleProject() {
		checkIfOpen();
		if (sampleProjects == null) sampleProjects = new SampleProjectClient(this);
		return sampleProjects;
	}

	/**
	 * Returns a SampleTypeClient to be used for retrieving SampleType resources from this Pinery client.
	 * 
	 * @return SampleTypeClient
	 */
	public SampleTypeClient getSampleType() {
		checkIfOpen();
		if (sampleTypes == null) sampleTypes = new SampleTypeClient(this);
		return sampleTypes;
	}

	/**
	 * Returns a SequencerRunClient to be used for retrieving SequencerRun resources from this Pinery client.
	 * 
	 * @return SequencerRunClient
	 */
	public SequencerRunClient getSequencerRun() {
		checkIfOpen();
		if (sequencerRuns == null) sequencerRuns = new SequencerRunClient(this);
		return sequencerRuns;
	}

	/**
	 * Returns a UserClient to be used for retrieving User resources from this Pinery client.
	 * 
	 * @return UserClient
	 */
	public UserClient getUser() {
		checkIfOpen();
		if (users == null) users = new UserClient(this);
		return users;
	}

	/**
	 * Returns a AttributeNameClient to be used for retrieving AttributeName resources from this Pinery client.
	 * 
	 * @return AttributeNameClient
	 */
	public AttributeNameClient getAttributeName() {
		checkIfOpen();
		if (attributeNames == null) attributeNames = new AttributeNameClient(this);
		return attributeNames;
	}

	/**
	 * Returns a ChangeLogClient to be used for retrieving ChangeLog resources from this Pinery client.
	 * 
	 * @return ChangeLogClient
	 */
	public ChangeLogClient getChangeLog() {
		checkIfOpen();
		if (changeLogs == null) changeLogs = new ChangeLogClient(this);
		return changeLogs;
	}

	/**
	 * Returns a InstrumentClient to be used for retrieving Instrument resources from this Pinery client.
	 * 
	 * @return InstrumentClient
	 */
	public InstrumentClient getInstrument() {
		checkIfOpen();
		if (instruments == null) instruments = new InstrumentClient(this);
		return instruments;
	}

	/**
	 * Returns a InstrumentModelClient to be used for retrieving InstrumentModel resources from this Pinery client.
	 * 
	 * @return InstrumentModelClient
	 */
	public InstrumentModelClient getInstrumentModel() {
		checkIfOpen();
		if (instrumentModels == null) instrumentModels = new InstrumentModelClient(this);
		return instrumentModels;
	}

	/**
	 * Returns a OrderClient to be used for retrieving Order resources from this Pinery client.
	 * 
	 * @return OrderClient
	 */
	public OrderClient getOrder() {
		checkIfOpen();
		if (orders == null) orders = new OrderClient(this);
		return orders;
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
