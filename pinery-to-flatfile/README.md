# Pinery-to-Flatfile Exporter

This is a small Java app for reading all LIMS data from a Pinery service and writing it to flat files. The flat files
are compatible for use in a flatfile-sourced Pinery service.

## Build

    mvn clean install

## Usage

    java -jar pinery-to-flatfile.jar <pinery-base-url> <output-dir> [filename-suffix]
    
| Parameter | Description | Example | Required? |
| --------- | ----------- | ------- | --------- |
| pinery-base-url | Base URL of Pinery service to pull data from | http://localhost:8080/pinery | yes |
| output-dir | Local directory to write files to | ~/flatfiles | yes |
| filename-suffix | Tag to include in filenames; Filenames will end in "_\<suffix\>.tsv" instead of just ".tsv" | dump | no |

## File Format

Output files are tab-separated value (tsv) files, for the most part. Additional patterns are used to represent lists
and key:value pairs within a field.

### Lists

Items are separated by commas (,) and the list is contained within brackets ([]). Example:

    [1,2,3,4,5]

List elements may be integers or key:value pair sets. All elements in a list must be of the same type.

### Key:Value Pairs

A set of pairs is contained within curly braces ({}). Pairs are separated by pipes (|), and a pair is in the format
*key=value*. The characters "{}[]|\\" are escaped with a single backslash (\\) in the value. Example:

    {key1=Abc|key2=1\{2\}3}

Key:value pair sets may be the elements in a list. The value in a key:value pair may also be a list.

## Output Files

#### samples.tsv

Contains all sample data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | int | ID from LIMS |
| name | String | Sample name |
| description | String |  |
| tubeBarcode | String |  |
| storageLocation | String |  |
| sampleType | String |  |
| createdDate | Date | format: 2015-12-01T17:16:32-04:00 |
| createdUserId | int | ID of user who created this sample |
| modifiedDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modifiedUserId | int | ID of user who last modified this sample |
| parentIds | List of int | IDs of parent samples |
| childIds | List of int | IDs of child samples |
| projectName | String |  |
| archived | boolean |  |
| status | key:value set | contains exactly 2 keys: 'name' and 'state' |
| volume | Float |  |
| concentration |  |
| preparationKit | key:value set | possible keys: 'name' and 'description' |
| attributes | key:value set | Any additional sample attributes. Attribute names are used as the keys |

#### changes.tsv

Contains all sample changelog records. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| sampleId | int | LIMS ID of sample to which this change was applied |
| action | String | Change details |
| createdDate | Date | Date of change. Format: 2015-12-01T17:16:32-04:00 |
| createdUserId | int | ID of user who made this change |

#### instruments.tsv

Contains all instrument data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | int | ID from LIMS |
| name | String | Instrument name |
| createdDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modelId | int | LIMS ID of this instrument's instrument model |
| modelName | String | Instrument model name |
| modelCreatedDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modelCreatedUserId | int | ID of user who created the InstrumentModel |
| modelModifiedDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modelModifiedUserId | int | ID of user who last modified the InstrumentModel |

#### runs.tsv

Contains all sequencer run data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | int | ID from LIMS |
| name | String | Run name |
| createdDate | Date | format: 2015-12-01T17:16:32-04:00 |
| createdUserId | ID of user who created this run |
| instrumentId | int | LIMS ID of instrument used for this run |
| instrumentName | String | Name of instrument usec for this run |
| state | String | Current state of run. Should be one of {Running, Completed, Failed, Unknown} |
| barcode | String | Run barcode |
| positions | List\* | See below |

\* positions field contains a list containing key:value pair sets, which contain more lists containing key:value pair sets!

    [{position=1|samples=[{id=123}]},{position=2|samples=[{id=200|barcode=AAAAAA},{id=201|barcode=CCCCCC}]}]

#### orders.tsv

Contains all order data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | int | ID from LIMS |
| projectName | String | Name of project which this order is a part of |
| status | String | Order status |
| platformName | String | Name of instrument model to use for this order |
| createdDate | Date | format: 2015-12-01T17:16:32-04:00 |
| createdUserId | ID of user who created this order |
| modifiedDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modifiedUserId | ID of user who last modified this order |
| samples | List\* | See below |

\* samples field example:

    [{id=123|barcode=AAAAAA|attributes={Read Length=2x151|Reference=Human hg19 random}},{id=14737|barcode=TAGCTT|attributes={Read Length=2x151|Reference=Human hg19 random}}]

#### users.tsv

Contains all user data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | int | ID from LIMS |
| title | String ||
| firstName | String ||
| lastName | String ||
| institution | String ||
| phone | String ||
| email | String ||
| comment | String ||
| archived | boolean ||
| createdDate | Date | format: 2015-12-01T17:16:32-04:00 |
| createdUserId | int | ID of user who created this user |
| modifiedDate | Date | format: 2015-12-01T17:16:32-04:00 |
| modifiedUserId | int | ID of user who last modified this user |

#### boxes.tsv

Contains all box data. Fields:

| Field | Type | Notes |
| ----- | ---- | ----- |
| id | long | ID from LIMS |
| name | String ||
| description | String ||
| location | String ||
| rows | int | number of rows in box |
| columns | int | number of columns in box |
| samples | List\* | See below |

\* samples field example:

    [{position=A01|sampleId=123},{position=A02|sampleId=456},{position=H12|sampleId=789}]

#### Omitted Data

The following data is NOT written to the output files.

* InstrumentModels that have no Instruments
