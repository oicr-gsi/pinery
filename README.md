# Pinery

Pinery is a webservice that provides a LIMS abstraction layer.

## Build

Build all modules, running all unit tests and integration tests:

    mvn clean install -DskipITs=false

## Components

### Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This repository includes
implementations that read data from Geospiza GeneSifter Lab Edition (GSLE), MISO-LIMS, and .tsv
flat files.

[More Info](pinery-ws/README.md)

### Java Client

The Java Client simplifies consumption of the Pinery webservice from within a Java app.

[More Info](pinery-client/README.md)

### Flat file writer

The Pinery-to-FlatFile utility creates a dump of all data from one LIMS into a set of flat files.

[More Info](pinery-to-flatfile/README.md)

## Contributing

* Maven 3 and JDK 7 are required
* Nothing should be committed directly to a versioned branch. Make a pull request when your work
  is ready to be merged
* Pull requests must pass the continuous integration (CI) build, and be reviewed before merging

**WARNING**: Be careful not to commit your own API keys or database credentials. To better avoid
this, favour external configuration, as detailed in the [webservice README](pinery-ws/README.md). 

## Support

For support, please file an issue on the [Github project](https://github.com/oicr-gsi/pinery) or
send an email to [gsi.oicr.on.ca](mailto:gsi@oicr.on.ca)
