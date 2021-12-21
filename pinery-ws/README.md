# Pinery Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This is an API that requires
implementation such as the one by [MISO-LIMS](https://github.com/miso-lims/miso-lims/tree/develop/pinery-miso).

## Minimum Requirements

* Maven 3
* JDK 11

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

## Deploying to Tomcat

### Tomcat Configuration

Tomcat 9 or is the recommended servlet container for Pinery.

You may wish to set the timezone explicitly, as not all source LIMS provide a time zone. It may also
be necessary to increase the JVM memory. You can alter these by configuring Tomcat's JAVA_OPTS. e.g.

```
JAVA_OPTS="-Duser.timezone=GMT -Djava.awt.headless=true -Xmx6144m -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC"
```

### Deploying

1. copy the example `.properties` file and external `context.xml` from the Pinery implementation
   you're using to `$CATALINA_HOME/conf/Catalina/localhost/`, where `$CATALINA_HOME` is your Tomcat
   base directory
2. rename `context.xml` to match the context root of your deployment. E.g. if you're deploying to
   `/pinery`, call the context file `pinery.xml`. If you're deploying to the root address, call the
   file `ROOT.xml`
3. Configure options specific to the source LIMS in the properties file
4. Copy the built `.war` file from the Pinery implemention to `$CATALINA_HOME/webapps/`, naming it
   to match your `context.xml` above. If Tomcat is configured to autodeploy, the webapp will be
   (re)deployed automatically; otherwise, deploy the webapp manually
   * **WARNING**: In some cases with autodeploy enabled, Tomcat may delete the context XML during redeployment.
     You can prevent this by stopping tomcat before copying the WAR into the webapps directory, or by making the
     file immutable via `chattr +i <filename>`
