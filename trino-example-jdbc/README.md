#set( $H = '#' )
Trino Plugin
============

[![Build Status](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/release.yaml/badge.svg)](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/release.yaml)

This is a [Trino](http://trino.io/) plugin that provides a connector.

# Quick Start

To run a Docker container with the connector, run the following:
```bash
docker run \
  -d \
  --name ${artifactId} \
  -p 8080:8080 \
  ${githubOwner}/${githubRepo}:0.1
```

Then use your favourite SQL client to connect to Trino running at http://localhost:8080

$H$H Usage

Download one of the ZIP packages, unzip it and copy the `${artifactId}-0.1` directory to the plugin directory on every node in your Trino cluster.
Create a `${connectorName}.properties` file in your Trino catalog directory and set all the required properties.

```
connector.name=${connectorName}
```

After reloading Trino, you should be able to connect to the `${connectorName}` catalog.

$H$H Build

Run all the unit tests:
```bash
mvn test
```

Creates a deployable zip file:
```bash
mvn clean package
```

Unzip the archive from the target directory to use the connector in your Trino cluster.
```bash
unzip target/*.zip -d ${PLUGIN_DIRECTORY}/
mv ${PLUGIN_DIRECTORY}/${artifactId}-* ${PLUGIN_DIRECTORY}/${artifactId}
```

$H$H Debug

To test and debug the connector locally, run the `${classPrefix}QueryRunner` class located in tests:
```bash
mvn test-compile exec:java -Dexec.mainClass="${groupId}.${classPrefix}QueryRunner" -Dexec.classpathScope=test
```

And then run the Trino CLI using `trino --server localhost:8080 --no-progress` and query it:
```
trino> show catalogs;
 Catalog
---------
 ${connectorName}
 system
(2 rows)

trino> show tables from ${connectorName}.default;
   Table
------------
 single_row
(1 row)

trino> select * from ${connectorName}.default.single_row;
 id |     type      |  name
----+---------------+---------
 x  | default-value | my-name
(1 row)
```
