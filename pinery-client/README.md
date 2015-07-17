#Pinery Client (Java)

This is a Java client for accessing the [Pinery API](https://wiki.oicr.on.ca/display/SEQPROD/Pinery+User+Guide). Pinery is a read-only webservice for retrieving LIMS data.

##Testing

```
mvn test -Dpinery-url="http://pinery.service.url:8888/pinery/"
```

Test data was pulled from [Staging LIMS](http://plims3.res.oicr.on.ca), so many tests are likely to fail if the webservice used is pointing at a different LIMS. If omitted, pinery-url defaults to http://localhost:8888/pinery-ws .

##Usage

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

##Support

For support, please file an issue on the [Github project](https://github.com/seqprodbio) or send an email to gsi@oicr.on.ca .
