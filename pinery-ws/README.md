# Pinery Webservice

Pinery is a read-only webservice that pulls information from a LIMS. This is an API that requires
implementation such as the one by [MISO-LIMS](https://github.com/miso-lims/miso-lims/tree/develop/pinery-miso).

## Minimum Requirements

- Maven 3
- JDK 17
- Tomcat 10

## Deploying to Tomcat

### Tomcat Configuration

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
   - **WARNING**: In some cases with autodeploy enabled, Tomcat may delete the context XML during redeployment.
     You can prevent this by stopping tomcat before copying the WAR into the webapps directory, or by making the
     file immutable via `chattr +i <filename>`
