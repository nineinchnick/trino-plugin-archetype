# trino-plugin-archetype

Maven archetype to create new Trino plugins. It provides:
* source code to build a plugin that defines one example table called `single_row`, with 3 columns: `id`, `type` and `name`, that always returns a single row with values `x`, `default-value` (from the config class) and `my-name`
* integration tests for this plugin, that run it and performs a `SELECT` query
* a basic `README.md`
* a `LICENSE` file with the Apache License 2.0
* Maven wrapper
* a `Dockerfile` to build an image based on Trino, but without any other plugins
* a `docker-build.sh` script to build and push the image with all the required build arguments, like the version number
* Github Actions (GHA) workflows for checking pull-requests and releasing new versions on every push to master

> Note that the GHA workflows do not publish to Maven Central yet, only to Github's Maven repository in the same repository

## Usage

This archetype is not yet published to Maven Central, so install it first:

```bash
mvn install
```

Then run the following, which will create a new directory with the plugin project:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=pl.net.was \
  -DarchetypeArtifactId=trino-plugin-archetype \
  -DarchetypeVersion=1.9 \
  -DgroupId=$my_group_id \
  -DartifactId=$my_artifact_id \
  -DclassPrefix=$class_prefix \
  -DconnectorName=$connector_name \
  -DgithubOwner=$github_owner \
  -DgithubRepo=$github_repo
```

Create a Github repository to host the plugin, then make the initial commit and push it:

```bash
cd <my-artifactId>
git init
git add --all .
git commit -m "Initial commit"
git branch -M main
git remote add origin git@github.com:nineinchnick/trino-faker.git
git push -u origin main
```

> Note that the release workflow requires the following secrets to be set:
> * `DOCKERHUB_USERNAME`
> * `DOCKERHUB_TOKEN`
