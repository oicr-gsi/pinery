# Pinery Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This repository includes
implementations that read data from Geospiza GeneSifter Lab Edition (GSLE), and .tsv flat files.

## Minimum Requirements

* Maven 3
* JDK 7

## Configuration

Each LIMS has its own set of options, which are detailed in the appropriate module.

* [GSLE](../pinery-lims-gsle)
* [Flat Files](../pinery-lims-flatfile)
* External modules may also be used

### Internal Configuration

With internal configuration, all configuration is included in the WAR file. Pinery must be packaged
separately for each data source, and rebuilt if any properties are changed. Internal configuration
is not possible if using an external LIMS implementation.

1. Change the spring default profile in **pinery-ws/src/main/webapp/WEB-INF/web.xml**. Acceptable
values are listed in the table below.

    ```
    <context-param>
      <param-name>spring.profiles.default</param-name>
      <param-value>gsle</param-value>
    </context-param>
    ```

2. Configure options specific to the source LIMS in the properties file specific to the implementation,
as listed in the table below.

| Source LIMS | Spring Profile | Properties File |
| ----------- | -------------- | --------------- |
| Geospiza | gsle | pinery-lims-gsle/src/main/resources/gsle.properties |
| Flat Files | flatfile | pinery-lims-flatfile/src/main/resources/flatfile.properties |

### External Configuration

External configuration makes it simpler to manage multiple Pinery instances and redeploy them without
having to modify files in the git repository and risk accidentally committing API keys or database
credentials every time.

1. copy the **.properties** file and **context-example.xml** from the **src/main/resources directory**
of the pinery-lims-<LIMS> module for the LIMS you're using to **$CATALINA_HOME/conf/Catalina/localhost/**,
where **$CATALINA_HOME** is your Tomcat base directory
2. rename **context-example.xml** to match the context root of your deployment. E.g. if you're
deploying to **/pinery-gsle**, call the context file **pinery-gsle.xml**
3. Configure options specific to the source LIMS in the properties file

### Running with Jetty

For a simple deployment, a standalone Jetty server can be launched via Maven. Note that this
option requires internal configuration and does not support external Lims implementations.

1. Configure Pinery using internal configuration, as detailed above
2. Build the entire Pinery project: `mvn clean install -DskipITs=false`)
3. Navigate to pinery/pinery-ws/
4. Run command mvn jetty:run
5. Jetty will display the resulting webservice base URL and port

### Running in Tomcat

Tomcat 7/8 is the recommended servlet container for Pinery as it offers greater flexibility, mainly
related to using external configuration.

You may wish to set the timezone explicitly, as not all source LIMS provide a time zone. It may also
be necessary to increase the JVM memory. You can alter these by configuring Tomcat's JAVA_OPTS. e.g.

```
JAVA_OPTS="-Duser.timezone=GMT -Djava.awt.headless=true -Xmx6144m -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC"
```

1. Configure Pinery using either internal or external configuration, as detailed above
2. Build the entire Pinery project: `mvn clean install -DskipITs=false`
3. Copy the **.war** file from **pinery/pinery-ws/target/** into your webapps directory. If Tomcat is
configured to autodeploy, the webapp will be (re)deployed automatically; otherwise, deploy the webapp manually
   * The name of the XML file should match the name of the context root, which by default will be the same
   as the WAR name. If you would like to deploy to /pinery-gsle, name your WAR file **pinery-gsle.war**, and your
   context file **pinery-gsle.xml**
   * **WARNING**: In some cases with autodeploy enabled, Tomcat may delete the context XML during redeployment.
   You can prevent this by stopping tomcat before copying the WAR into the webapps directory, or by making the
   file immutable via `chattr +i <filename>`

### External LIMS Implementations

LIMS implementations external to this project may be used with the `external` Spring profile.

#### External project requirements

* Provide an implementation of the `ca.on.oicr.pinery.api.Lims` interface
* Include an internal spring config XML in **src/main/resources/** declaring a bean of the Lims
implementation type. Any other wiring necessary for the implementation may be included. This file should
be named specifically to avoid collisions. **pinery-mylims-config.xml** is an ideal name, whereas
a name like **context.xml** is more likely to cause problems with other similarly named files in the
classpath.
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

#### Deploying to Tomcat

* add an external context config xml as described for _External Configuration_ above. This external
  config should set the active Spring profile to `external`, reference the internal spring config xml,
  and optionally indicate a properties file if one is required by the implementation. An example of
  this file should be included with the implementation. Here is an example:

    ```
    <Context>
        <Parameter name="spring.profiles.active" value="external" override="false"/>
        <Parameter name="pinery.external.springConfigFile" value="mylims-config.xml"/>
        <Parameter name="mylims.propertiesFile" value="file:${CATALINA_HOME}/conf/Catalina/localhost/pinery-mylims.properties" override="false"/>
    </Context>
    ```
* build your external implementation and deploy the resulting WAR
