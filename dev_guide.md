# Pinery Development Guide

## Modules

Pinery is composed of several modules:

| Module | Contains |
| ------ | -------- |
| pinery-api | data class interfaces |
| pinery-lims | default implementations of all of the data class interfaces |
| pinery-service | service layer interfaces and implementations; used by the webservice to retrieve data from a LIMS or cache |
| pinery-ws-dto | data transfer objects used in responses from the webservice |
| pinery-ws | web app using Spring Framework |
| pinery-client | library that can be used for client applications to retrieve data from Pinery |

## Testing

* Pinery-client has unit tests for all client classes
* Pinery-service has unit tests for the provenance services
* Pinery-ws-dto has unit tests for some of the DTO conversions
* Pinery-ws has unit tests for the resource (Spring controller) classes
  * The versioned provenance transform tests are especially important - every provenance version
    being served should have a test that checks to ensure the hash never changes

## Writing a New Implementation

* Provide an implementation of the `ca.on.oicr.pinery.api.Lims` interface
* Include an internal spring config XML in `src/main/resources/` declaring a bean of the `Lims`
  implementation type. Any other wiring necessary for the implementation may be included. This file
  should be named specifically to avoid collisions. `pinery-mylims-config.xml` is an ideal name,
  whereas a name like `context.xml` is more likely to cause problems with other similarly named
  files in the classpath.
* Include an example external `context.xml` that can be copied when deploying to Tomcat. This file
  should set the active Spring profile to `external`, and provide the names of the internal spring
  config XML described above and the properties file that will contain any other configuration. For
  example:

  ```
  <Context>
    <Parameter name="spring.profiles.active" value="external" override="false"/>
    <Parameter name="pinery.external.springConfigFile" value="mylims-config.xml"/>
    <Parameter name="pinery.propertiesFile" value="file:${CATALINA_HOME}/conf/Catalina/localhost/pinery-mylims.properties" override="false"/>
  </Context>
  ```

* Include an example properties file containing any options that are necessary (DB connection info,
  API keys, etc.)
* Package the external module as a war and include pinery-ws as an overlay. Example Maven config:

    ```
    <packaging>war</packaging>

    <dependencies>
      <dependency>
        <groupId>ca.on.oicr</groupId>
        <artifactId>pinery-ws</artifactId>
        <version>${pinery.version}</version>
        <type>war</type>
        <scope>runtime</scope>
      </dependency>
      ...
    </dependencies>
    ```
