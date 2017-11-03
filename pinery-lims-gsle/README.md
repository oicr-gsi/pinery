# Pinery-GSLE

This Pinery LIMS implementation reads data from Geospiza GeneSifter Lab Edition via its own
webservice. This requires a GSLE API key.

## Configuration

The [properties file](src/main/resources/gsle.properties) contains the following options.

* **gsle.url**: base url of the GSLE server. e.g. `lims.res.oicr.on.ca`
* **gsle.key**: your API key. See below if you don't have an API key
* **gsle.DefaultRunDirectoryBaseDir**:  base directory for sequencer runs. This is used because
the GSLE data often contains only a relative path for run directories, and Pinery is expected
to provide an absolute path

The other options are IDs for queries that are saved in GSLE. Query IDs for OICR Production
GSLE are included in the default properties file.

**WARNING**: Be sure not to commit your API key if you modify the included properties file.

## API Key

To get your GSLE API Key

1. Log into the Geospiza LIMS
2. From the *System* tab, click *View Users* on the left.
3. Find your own user and click your username to open your profile
4. Your API Key will be listed as *Form API Key*
   * If no API Key is shown, you may need to ask your Geospiza LIMS administrator to generate
   one for you

## Installation

For installation instructions, see the [Pinery Webservice documentation](../pinery-ws/README.md).
