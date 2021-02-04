# Pinery-File

This Pinery LIMS implementation reads data from *.tsv flat files. These can be exported from
another Pinery source using the [Pinery-to-Flatfile utility](../pinery-to-flatfile). The format
of these files is also specified in the
[Pinery-to-Flatfile documentation](../pinery-to-flatfile/README.md)

## Configuration

The [properties file](src/main/resources/flatfile.properties) contains the following options.

* **flatfile.dir**: directory to read from (use absolute path). A complete set of flat files
must be included in this directory
* **flatfile.suffix**: useful if you have multiple sets of LIMS flat files in the same directory.
If you have a **samples_lims1.tsv** and a **samples_lims2.tsv**, you can set
`flatfile.suffix=_lims1.tsv` to read only the files ending with **_lims1.tsv**

## Installation

For installation instructions, see the [Pinery Webservice documentation](../pinery-ws/README.md).
