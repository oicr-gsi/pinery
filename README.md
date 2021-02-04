[![Build Status](https://travis-ci.org/oicr-gsi/pinery.svg?branch=master)](https://travis-ci.org/oicr-gsi/pinery) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=ca.on.oicr%3Apinery&metric=alert_status)](https://sonarcloud.io/dashboard?id=ca.on.oicr%3Apinery)

# Pinery

Pinery is a webservice that provides a LIMS abstraction layer. The main advantage of this is to avoid
tightly coupling your other applications with a particular data source. This means that your downstream
applications are not dependent on a specific LIMS' schema. You can write your applications once and they
will be compatible with any data source that has a Pinery implementation.

## Build

Build all modules, running all unit tests and integration tests:

    mvn clean install -DskipITs=false

## Components

### Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This repository includes
an implementation that read data from .tsv flat files.

[More Info](pinery-ws/README.md)

### Java Client

The Java Client simplifies consumption of the Pinery webservice from within a Java app.

[More Info](pinery-client/README.md)

### Flat file writer

The Pinery-to-FlatFile utility creates a dump of all data from one LIMS into a set of flat files.

[More Info](pinery-to-flatfile/README.md)

## Contributing

* Maven 3 and JDK 8 are required
* Nothing should be committed directly to a versioned branch. Make a pull request when your work
  is ready to be merged
* Pull requests must pass the continuous integration (CI) build, and be reviewed before merging

**WARNING**: Be careful not to commit your own API keys or database credentials. To better avoid
this, favour external configuration, as detailed in the [webservice README](pinery-ws/README.md). 

## License 

Pinery is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Pinery is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

The GNU General Public License Version 3 is included in this repository as [LICENSE](LICENSE).
It is also available online at <http://www.gnu.org/licenses/>.

## Support

For support, please file an issue on the [Github project](https://github.com/oicr-gsi/pinery) or
send an email to [gsi.oicr.on.ca](mailto:gsi@oicr.on.ca)
