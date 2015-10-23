package ca.on.oicr.pinery.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
		Response response = null;
		try {
			response = mainClient.callPinery(resourceUrl);
			T resource = response.readEntity(resourceClass);
			if (resource == null) {
			  // The server should really return a 404 error if a specific resource isn't found
			  throw new HttpResponseException(resourceUrl, Status.NOT_FOUND.getStatusCode());
			}
			return resource;
		} 
		finally {
			if (response != null) response.close();
		}
	}
	
	/**
	 * Retrieve multiple resources (objects) from Pinery
	 * 
	 * @param resourceUrl the resource URL, relative to the base Pinery URL
	 * @return the requested resources, or an empty list if no resources of the requested type are available
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	protected List<T> getResourceList(String resourceUrl) throws HttpResponseException {
		Response response = null;
		
		try {
			response = mainClient.callPinery(resourceUrl);
			T[] entities = response.readEntity(arrayClass);
			if (entities == null || entities.length == 0) {
			  return new ArrayList<T>();
			}
			else {
			  return Arrays.asList(entities);
			}
		}
		finally {
			if (response != null) response.close();
		}
	}
	
}
