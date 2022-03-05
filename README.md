# trino-plugin-archetype

Maven archetype to create new Trino plugins

## Usage

This archetype is not yet published to Maven Central, so install it first:

```bash
./mvnw install
```

```
mvn archetype:generate \
  -DarchetypeGroupId=pl.net.was \
  -DarchetypeArtifactId=trino-plugin-archetype \
  -DarchetypeVersion=1.0 \
  -DgroupId=<my.groupid> \
  -DartifactId=<my-artifactId>
  -DgithubRepo=<owner/repo>
```
