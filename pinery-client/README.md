#Pinery Client (Java)

This is a Java client for accessing the [Pinery API](https://wiki.oicr.on.ca/display/SEQPROD/Pinery+User+Guide). Pinery is a read-only REST webservice for retrieving LIMS data. The client makes available all REST endpoints provided by Pinery.

##Usage

After importing the client into your code, usage as as simple as creating a PineryClient that points to a Pinery webservice URL, and making requests.

1. Instantiate a PineryClient object:
    
    ```
    PineryClient pinery = new PineryClient("http://localhost:8888/pinery-ws");
    ```

2. Get resources. The example below demonstrates the types of calls available and the patterns they follow. See the javadocs for more information regarding specific classes.
    
    ```
    // Full list
    List<SampleDto> sampleList = pinery.getSample().all();
    
    // Single field filter
    SampleDto sample = pinery.getSample().byId(22);
    
    // Multiple field filter
    List<SampleDto> filteredSampleList = pinery.getSample().allFiltered(
        new SamplesFilter()
            .withArchived(false)
            .withDateBefore(myDateTime);
    );
    ```

3. Remember to close the PineryClient when it is no longer needed.
    
    ```
    pinery.close();
    ```

##Testing

```
mvn test -Dpinery-url="http://pinery.service.url:8888/pinery/"
```

Test data was pulled from [Staging LIMS](http://plims3.res.oicr.on.ca), so many tests are likely to fail if the webservice used is pointing at a different LIMS. If omitted, pinery-url defaults to http://localhost:8888/pinery-ws . See [Pinery Development](https://wiki.oicr.on.ca/display/SEQPROD/Pinery+Development) for information on running a local Pinery webservice.


##Support

For support, please file an issue on the [Github project](https://github.com/seqprodbio) or send an email to gsi@oicr.on.ca .
