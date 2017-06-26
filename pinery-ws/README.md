# Pinery Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This repository includes
implementations that read data from Geospiza GeneSifter Lab Edition (GSLE), MISO-LIMS, and .tsv
flat files.

## Minimum Requirements

* Maven 3
* JDK 7

## Configuration

Each LIMS has its own set of options, which are detailed in the appropriate module.

* [GSLE](../pinery-lims-gsle)
* [MISO](../pinery-lims-miso)
* [Flat Files](../pinery-lims-flatfile)

### Internal Configuration

With internal configuration, all configuration is included in the WAR file. Pinery must be packaged
separately for each data source, and rebuilt if any properties are changed.

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
| MISO | miso | pinery-lims-miso/src/main/resources/miso.properties |
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
option requires internal configuration.

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
