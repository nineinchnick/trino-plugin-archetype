ARG TRINO_VERSION
FROM nineinchnick/trino-core:$TRINO_VERSION

ARG VERSION

ADD target/${artifactId}-$VERSION/ /usr/lib/trino/plugin/${connectorName}/
ADD catalog/${connectorName}.properties /etc/trino/catalog/${connectorName}.properties
