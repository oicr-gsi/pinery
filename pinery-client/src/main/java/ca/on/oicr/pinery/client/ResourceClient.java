package ca.on.oicr.pinery.client;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

/**
 * Subtypes of this class should offer methods for retrieving their resource type from Pinery by 
 * levereging the {@link #getResource(String) getResource} and {@link #getResourceList(String) getResourceList} methods. 
 * This class relies on its associated PineryClient to make the actual GET requests, and provides the type 
 * specification needed between the PineryClient and subtype.
 * 
 * @author dcooke
 *
 * @param <T> The type of resource that this client will retrieve from Pinery
 */
public abstract class ResourceClient<T> {
	
	private final Class<T> resourceClass;
	private final Class<T[]> arrayClass;
	private final PineryClient mainClient;
	
	/**
	 * Creates a ResourceClient for retrieving a specific resource (object) type from Pinery
	 * 
	 * @param resourceClass class of resource to retrieve
	 * @param arrayClass resource array class
	 * @param mainClient the PineryClient, which will make the actual requests
	 */
	public ResourceClient(Class<T> resourceClass, Class<T[]> arrayClass, PineryClient mainClient) {
		if (resourceClass == null) throw new IllegalArgumentException("Resource class cannot be null");
		if (arrayClass == null) throw new IllegalArgumentException("Array class cannot be null");
		if (mainClient == null) throw new IllegalArgumentException("PineryClient cannot be null");
		
		this.resourceClass = resourceClass;
		this.arrayClass = arrayClass;
		this.mainClient = mainClient;
	}
	
	/**
	 * Retrieve a single resource (object) from Pinery
	 * 
	 * @param resourceUrl the resource URL, relative to the base Pinery URL
	 * @return the requested resource
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	protected T getResource(String resourceUrl) throws HttpResponseException {
		Response response = mainClient.callPinery(resourceUrl);
		T resource = response.readEntity(resourceClass);
		response.close();
		return resource;
	}
	
	/**
	 * Retrieve multiple resources (objects) from Pinery
	 * 
	 * @param resourceUrl the resource URL, relative to the base Pinery URL
	 * @return the requested resources
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	protected List<T> getResourceList(String resourceUrl) throws HttpResponseException {
		Response response = mainClient.callPinery(resourceUrl);
		
		T[] entities = response.readEntity(arrayClass);
		return Arrays.asList(entities);
	}
	
}
