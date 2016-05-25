package ca.on.oicr.pinery.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.Closeable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

/**
 * This is the main class used for retrieving data from the Pinery webservice. It contains "child" clients for each 
 * available resource type.
 * 
 * @author dcooke
 */
public class PineryClient implements Closeable {
	
	private final String pineryBaseUrl;
	private final Client client;
	private boolean open = false;
	
	// Individual resource clients lazily-loaded upon first resource of type requested 
	private SampleClient samples;
	private ProjectClient projects;
	private SampleTypeClient sampleTypes;
	private SequencerRunClient sequencerRuns;
	private UserClient users;
	private AttributeNameClient attributeNames;
	private ChangeLogClient changeLogs;
	private InstrumentClient instruments;
	private InstrumentModelClient instrumentModels;
	private OrderClient orders;
        private SampleProvenanceClient sampleProvenance;
        private LaneProvenanceClient laneProvenance;
	
	/**
	 * Creates a new PineryClient to communicate with the Pinery web service at the specified URL. Note that 
	 * the client is considered "open" upon creation, and the close() method should be called when it is no 
	 * longer needed.
	 * 
	 * @param baseUrl Pinery webservice URL
	 */
	public PineryClient(String baseUrl) {
		this(baseUrl, false);
	}
	
	/**
	 * Creates a new PineryClient to communicate with the Pinery web service at the specified URL. Note that 
	 * the client is considered "open" upon creation, and the close() method should be called when it is no 
	 * longer needed.
	 * 
	 * @param baseUrl Pinery webservice URL
	 * @param ignoreHttpsWarnings if true, certificate and hostname errors will be ignored
	 */
	public PineryClient(String baseUrl, boolean ignoreHttpsWarnings) {
		this.pineryBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
		this.client = ignoreHttpsWarnings ? PineryClient.getInsecureClient() : PineryClient.getSecureClient();
		// Register provider manually because it Was not registering automatically in dependent projects
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JodaModule());
                ResteasyJackson2Provider provider = new ResteasyJackson2Provider();
                provider.setMapper(objectMapper);
		this.client.register(provider);
		this.open = true;
	}
	
	protected static Client getSecureClient() {
		return ClientBuilder.newBuilder().build();
	}
	
	protected static Client getInsecureClient() {
	    SSLContext sslcontext;
		try {
			sslcontext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		X509TrustManager trustMgr = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		};
		
		HostnameVerifier verifier = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
	    };
		
		try {
			sslcontext.init(null, new TrustManager[] {trustMgr}, new java.security.SecureRandom());
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		}
	    
	    return ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier(verifier).build();
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
	 * @return a SampleClient to be used for retrieving Sample resources from this Pinery client
	 */
	public SampleClient getSample() {
		checkIfOpen();
		if (samples == null) samples = new SampleClient(this);
		return samples;
	}

	/**
	 * @return a ProjectClient to be used for retrieving Project resources from this Pinery client.
	 */
	public ProjectClient getSampleProject() {
		checkIfOpen();
		if (projects == null) projects = new ProjectClient(this);
		return projects;
	}

	/**
	 * @return a SampleTypeClient to be used for retrieving SampleType resources from this Pinery client.
	 */
	public SampleTypeClient getSampleType() {
		checkIfOpen();
		if (sampleTypes == null) sampleTypes = new SampleTypeClient(this);
		return sampleTypes;
	}

	/**
	 * @return a SequencerRunClient to be used for retrieving SequencerRun resources from this Pinery client.
	 */
	public SequencerRunClient getSequencerRun() {
		checkIfOpen();
		if (sequencerRuns == null) sequencerRuns = new SequencerRunClient(this);
		return sequencerRuns;
	}

	/**
	 * @return a UserClient to be used for retrieving User resources from this Pinery client.
	 */
	public UserClient getUser() {
		checkIfOpen();
		if (users == null) users = new UserClient(this);
		return users;
	}

	/**
	 * @return an AttributeNameClient to be used for retrieving AttributeName resources from this Pinery client.
	 */
	public AttributeNameClient getAttributeName() {
		checkIfOpen();
		if (attributeNames == null) attributeNames = new AttributeNameClient(this);
		return attributeNames;
	}

	/**
	 * @return a ChangeLogClient to be used for retrieving ChangeLog resources from this Pinery client.
	 */
	public ChangeLogClient getChangeLog() {
		checkIfOpen();
		if (changeLogs == null) changeLogs = new ChangeLogClient(this);
		return changeLogs;
	}

	/**
	 * @return an InstrumentClient to be used for retrieving Instrument resources from this Pinery client.
	 */
	public InstrumentClient getInstrument() {
		checkIfOpen();
		if (instruments == null) instruments = new InstrumentClient(this);
		return instruments;
	}

	/**
	 * @return an InstrumentModelClient to be used for retrieving InstrumentModel resources from this Pinery client.
	 */
	public InstrumentModelClient getInstrumentModel() {
		checkIfOpen();
		if (instrumentModels == null) instrumentModels = new InstrumentModelClient(this);
		return instrumentModels;
	}

	/**
	 * @return an OrderClient to be used for retrieving Order resources from this Pinery client.
	 */
	public OrderClient getOrder() {
		checkIfOpen();
		if (orders == null) orders = new OrderClient(this);
		return orders;
	}
        
        /**
	 * @return an SampleProvenanceClient to be used for retrieving SampleProvenance resources from this Pinery client.
	 */
        public SampleProvenanceClient getSampleProvenance() {
                checkIfOpen();
                if(sampleProvenance == null) {
                    sampleProvenance = new SampleProvenanceClient(this);
                }
                return sampleProvenance;
        }

        /**
	 * @return an LaneProvenanceClient to be used for retrieving LaneProvenance resources from this Pinery client.
	 */
        public LaneProvenanceClient getLaneProvenance() {
                checkIfOpen();
                if(laneProvenance == null) {
                    laneProvenance = new LaneProvenanceClient(this);
                }
                return laneProvenance;
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
	@Override
	public void close() {
		if (open && client != null) {
			open = false;
			client.close();
			
			samples = null;
			projects = null;
			sampleTypes = null;
			sequencerRuns = null;
			users = null;
			attributeNames = null;
			changeLogs = null;
			instruments = null;
			instrumentModels = null;
			orders = null;
                        sampleProvenance = null;
                        laneProvenance = null;
		}
	}
	
}
